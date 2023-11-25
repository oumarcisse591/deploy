package com.deployment.deploy.Controller;

import com.deployment.deploy.Dao.ReceiptDao;
import com.deployment.deploy.Entity.Receipt;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ReportController {

    @Autowired
    private ReceiptDao receiptDao;


//    private ReportService reportService;

    @GetMapping("/reportPdf")
    public ResponseEntity getReceiptReport(@RequestParam("id") int theId, Model theModel) throws JRException, FileNotFoundException {
        Receipt theReceipt = receiptDao.findReceiptById(theId);
        String ref = theReceipt.getReceiptCode();
        Date date = theReceipt.getReceiptDate();
        String remitterName = theReceipt.getSociety().getSocietyName();
        String remitterAddress = theReceipt.getSociety().getSocietyAdress();
        String beneficiaryName = theReceipt.getBeneficiary().getBeneficiaryName();
        String beneficiaryAddress = theReceipt.getBeneficiary().getBeneficiaryAdress();
        String beneficiaryBank = theReceipt.getBeneficiaryBank();
        String beneficiaryBankAddress = theReceipt.getBeneficiaryBankAddress();
        String beneficiaryAccount = theReceipt.getBeneficiaryAccount();
        String swift = theReceipt.getSwift_code();
        double amount = theReceipt.getAmount();
        String currency = theReceipt.getCurrency();
        Date valueDate = theReceipt.getValueDate();
        String paymentReference = theReceipt.getMotifTransaction();
        ByteArrayOutputStream reportStream = generateReport(ref, date, remitterName, remitterAddress, beneficiaryName, beneficiaryAddress, beneficiaryBank, beneficiaryBankAddress, beneficiaryAccount, swift, amount, currency, valueDate, paymentReference);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(reportStream.toByteArray(), httpHeaders, HttpStatus.OK);
    }

    public ByteArrayOutputStream generateReport(String receiptCode, Date date, String remitterName, String remitterAddress, String BeneficiaryName, String beneficiaryAddress, String beneficiaryBank, String beneficiaryBankAddress, String beneficiaryAccount, String swift, double amount, String currency, Date valueDate, String paymentReference) throws FileNotFoundException, JRException {
        File file = ResourceUtils.getFile("classpath:recu.jrxml");
//        String filePath = "classpath:recu.jrxml";
        File downloadsDirectory = new File(System.getProperty("user.home"), "Downloads");
        String path = downloadsDirectory.getAbsolutePath();
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("receiptCode", receiptCode);
        parameters.put("receiptDate", date);
        parameters.put("remitterName", remitterName);
        parameters.put("remitterAddress", remitterAddress);
        parameters.put("beneficiaryName", BeneficiaryName);
        parameters.put("beneficiaryAddress", beneficiaryAddress);
        parameters.put("beneficiaryBank", beneficiaryBank);
        parameters.put("beneficiaryBankAddress", beneficiaryBankAddress);
        parameters.put("beneficiaryAccount", beneficiaryAccount);
        parameters.put("swift", swift);
        parameters.put("amount", amount);
        parameters.put("currency", currency);
        parameters.put("valueDate", valueDate);
        parameters.put("paymentReference", paymentReference);

        JasperReport report = JasperCompileManager.compileReport(String.valueOf(file));
        JasperPrint print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
//        JasperExportManager.exportReportToPdfFile(print, path+ receiptCode +"/report.pdf");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JRPdfExporter exporter = new JRPdfExporter();
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        configuration.setCompressed(true);
        exporter.setConfiguration(configuration);
        exporter.setExporterInput(new SimpleExporterInput(print));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        exporter.exportReport();
        return byteArrayOutputStream;
    }



}
