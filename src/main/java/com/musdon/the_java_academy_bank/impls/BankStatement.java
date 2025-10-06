package com.musdon.the_java_academy_bank.impls;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import com.musdon.the_java_academy_bank.Repos.TranscationRepo;
import com.musdon.the_java_academy_bank.Repos.UserRepo;
import com.musdon.the_java_academy_bank.dtos.EmailDetails;
import com.musdon.the_java_academy_bank.models.Transcation;
import com.musdon.the_java_academy_bank.models.User;
import com.musdon.the_java_academy_bank.services.EmailService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class BankStatement {

    @Autowired
    private final TranscationRepo transcationRepo;
    private EmailService emailService;

    @Autowired
    private final UserRepo userRepo;

    private static final String FILE = "E:/MyStatement.pdf";

    public List<Transcation> generateStatement(String accountNumber, String stateDate, String endDate) {
        LocalDate start = LocalDate.parse(stateDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

        // Null-safe transaction filter
        List<Transcation> transcationList = transcationRepo.findAll().stream()
                .filter(t -> t.getAccountNumber().equals(accountNumber))
                .filter(t -> t.getCreatedAt() != null)
                .filter(t -> !t.getCreatedAt().isBefore(start) && !t.getCreatedAt().isAfter(end))
                .toList();

        User user = userRepo.findByAccountNumber(accountNumber);
        String customerName = user.getFirstName() + " " + user.getLastName() + " " + user.getOtherName();

        Rectangle statementSize = PageSize.A4; // 55:11
        Document document = new Document(statementSize);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();

            // Bank Info Table
            PdfPTable bankInfoTable = new PdfPTable(1);
            PdfPCell bankName = new PdfPCell(new Phrase("The Java Academy Bank"));
            bankName.setBorder(0);
            bankName.setBackgroundColor(BaseColor.BLUE);
            bankName.setPadding(20f);
            bankInfoTable.addCell(bankName);

            PdfPCell bankAddress = new PdfPCell(new Phrase("72, Some Address, Lagos Nigeria"));
            bankAddress.setBorder(0);
            bankInfoTable.addCell(bankAddress);

            // Statement Info Table
            PdfPTable statementInfo = new PdfPTable(2);
            statementInfo.setWidthPercentage(100);
            statementInfo.addCell(new PdfPCell(new Phrase("Start Date: " + stateDate)));
            statementInfo.addCell(new PdfPCell(new Phrase("STATEMENT OF ACCOUNT")));
            statementInfo.addCell(new PdfPCell(new Phrase("End Date: " + endDate)));
            statementInfo.addCell(new PdfPCell(new Phrase("Customer Name: " + customerName)));
            statementInfo.addCell(new PdfPCell(new Phrase("Customer Address: " + user.getAddress())));

            // Transaction Table
            PdfPTable transactionTable = new PdfPTable(4); // Date, Type, Amount, Status, ModifiedAt
            transactionTable.addCell(new PdfPCell(new Phrase("DATE")));
            transactionTable.addCell(new PdfPCell(new Phrase("TRANSACTION TYPE")));
            transactionTable.addCell(new PdfPCell(new Phrase("TRANSACTION AMOUNT")));
            transactionTable.addCell(new PdfPCell(new Phrase("STATUS")));

            transcationList.forEach(t -> {
                transactionTable.addCell(new Phrase(t.getCreatedAt().toString()));
                transactionTable.addCell(new Phrase(t.getTranscationType()));
                transactionTable.addCell(new Phrase(t.getAmount().toString()));
                transactionTable.addCell(new Phrase(t.getStatus()));

            });

            // Add tables to document
            document.add(bankInfoTable);
            document.add(statementInfo);
            document.add(transactionTable);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close(); // closes PdfWriter + stream safely
            }
        }

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .subject("STATEMENT OF ACCOUNT")
                .messageBody("Kindly find your requested account statement attached!")
                .attachment(FILE)

                .build();
        emailService.sendEmailAlert(emailDetails);

        return transcationList;
    }
}
