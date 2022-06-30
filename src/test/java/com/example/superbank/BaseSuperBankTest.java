package com.example.superbank;

import com.example.superbank.model.UserAccount;
import com.example.superbank.repository.TransactionRepository;
import com.example.superbank.repository.UserAccountRepository;
import com.example.superbank.service.MathFeignService;
import com.example.superbank.service.TransactionService;
import com.example.superbank.service.UserAccountService;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

@Profile("test")
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest
public abstract class BaseSuperBankTest {

    @Autowired
    protected TransactionService transactionService;

    @Autowired
    protected TransactionRepository transactionRepository;

    @Autowired
    protected UserAccountService userAccountService;

    @Autowired
    protected UserAccountRepository userAccountRepository;

    @Autowired
    protected MathFeignService mathFeignService;

    private WireMockServer wm;

    protected static final BigDecimal COMMISSION_MIN_VALUE = BigDecimal.valueOf(250);

    protected static final String JAKE = "Jake";
    protected static final String BOB = "Bob";
    
    @BeforeEach
    void init() {
        wm = new WireMockServer(8081);
        wm.start();

        userAccountRepository.saveAll(Stream.of(new UserAccount(JAKE), new UserAccount(BOB)).collect(Collectors.toList()));
    }
    
    @AfterEach
    void stopServer() {
        wm.stop();
    }

    protected void stubFeignClientCommissionResponse(BigDecimal request, BigDecimal response) {
        wm.stubFor(get(urlPathMatching("/commission"))
                .withQueryParam("moneySum", equalTo(request.toPlainString()))
                .willReturn(
                        aResponse()
                                .withBody(response.toPlainString())
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                )
        );
    }
}
