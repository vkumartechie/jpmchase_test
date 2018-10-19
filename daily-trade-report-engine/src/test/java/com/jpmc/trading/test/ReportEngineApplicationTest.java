package com.jpmc.trading.test;

import com.jpmchase.trading.reportengine.ReportEngineApplication;
import com.jpmchase.trading.reportengine.dto.Currency;
import com.jpmchase.trading.reportengine.exception.ReportingEngineException;
import com.jpmchase.trading.reportengine.util.InstructionUtil;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Unit test for ReportEngineApplication.
 */
public class ReportEngineApplicationTest
    extends TestCase
{
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private ReportEngineApplication reportEngineApplication = new ReportEngineApplication();
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ReportEngineApplicationTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ReportEngineApplicationTest.class );
    }

    /**
     * This test is used to test all the incoming settlement report
     * @throws ReportingEngineException
     */
    public void testIncomingSettlementReport() throws ReportingEngineException {
        assertEquals("Success", reportEngineApplication.generateReport("ISR").getResponseMessage());
    }

    /**
     * This test is used to test all the outgoing settlement report
     * @throws ReportingEngineException
     */
    public void testOutcomingSettlementReport() throws ReportingEngineException {
        assertEquals("Success", reportEngineApplication.generateReport("OSR").getResponseMessage());
    }

    /**
     * This test is used to test  the incoming Ranking report
     * @throws ReportingEngineException
     */
    public void testIncomingRankingReport() throws ReportingEngineException {
        assertEquals("Success", reportEngineApplication.generateReport("IRR").getResponseMessage());
    }

    /**
     * This test is used to test the outgoing Ranking report
     * @throws ReportingEngineException
     */
    public void testOutgoingRankingReport() throws ReportingEngineException {
        assertEquals("Success", reportEngineApplication.generateReport("ORR").getResponseMessage());
    }

    /**
     * This test is used to test the invalid request report
     * @throws ReportingEngineException
     */
    public void testInvalidReport() throws ReportingEngineException {
        assertEquals("INVALID_INPUT", reportEngineApplication.generateReport("XYZ").getResponseMessage());
    }

    /**
     * This test is used to test all the incoming settlement report for gulf country which currency has AED or SAR
     * @throws ReportingEngineException
     */
    public void testAdjustedSettlementDateForAED() throws ReportingEngineException {
        LocalDate settlementDate = LocalDate.parse("10-12-2018", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        assertEquals("10-14-2018", InstructionUtil.getAdjustedSettlementDate(Currency.AED.name(), settlementDate).format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
    }

    /**
     * This test is used to test settlement date for gulf country which currency has AED or SAR and falls in weekend
     * @throws ReportingEngineException
     */
    public void testAdjustedSettlementDateInWeekendForSAR() throws ReportingEngineException {
        LocalDate settlementDate = LocalDate.parse("10-13-2018", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        assertEquals("10-14-2018", InstructionUtil.getAdjustedSettlementDate(Currency.SAR.name(), settlementDate).format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
    }

    /**
     * This test is used to test settlement date for gulf country which currency has AED or SAR and falls in weekend
     * @throws ReportingEngineException
     */
    public void testAdjustedSettlementDateInWeekdaysForSAR() throws ReportingEngineException {
        LocalDate settlementDate = LocalDate.parse("10-11-2018", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        assertEquals("10-11-2018", InstructionUtil.getAdjustedSettlementDate(Currency.SAR.name(), settlementDate).format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
    }

    /**
     * This test is used to test all the incoming settlement report for other country except gulf country which currency has AED or SAR
     * @throws ReportingEngineException
     */
    public void testAdjustedSettlementDateForOtherCurrencyInWeekdays() throws ReportingEngineException {
        LocalDate settlementDate = LocalDate.parse("10-12-2018", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        assertEquals("10-12-2018", InstructionUtil.getAdjustedSettlementDate(Currency.EUR.name(), settlementDate).format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
    }

    /**
     * This test is used to test all the incoming settlement report for other country except gulf country which currency has AED or SAR
     * @throws ReportingEngineException
     */
    public void testAdjustedSettlementDateForOtherCurrencyInWeekend() throws ReportingEngineException {
        LocalDate settlementDate = LocalDate.parse("10-13-2018", DateTimeFormatter.ofPattern("MM-dd-yyyy"));
        assertEquals("10-15-2018", InstructionUtil.getAdjustedSettlementDate(Currency.USD.name(), settlementDate).format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
    }

    /**
     * Test for exception
     * @throws ReportingEngineException
     */
    public void testTechnicalException() throws ReportingEngineException {
        assertEquals("TECHNICAL_ERROR", reportEngineApplication.generateReport(null).getResponseMessage());
    }
}
