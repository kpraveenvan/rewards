package com.retail.rewards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


import com.retail.rewards.entity.ConsumerTransaction;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

public class CSVHelper {
  public static String TYPE = "text/csv";

  public static boolean hasCSVFormat(MultipartFile file) {

    return TYPE.equals(file.getContentType());
  }

  public static List<ConsumerTransaction> csvToTutorials(InputStream is) {
    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
         CSVParser csvParser = new CSVParser(fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

      List<ConsumerTransaction> tutorials = new ArrayList<>();

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {
        ConsumerTransaction tutorial = new ConsumerTransaction(
              Long.parseLong(csvRecord.get("consumerId")),
              csvRecord.get("first_name"),
              csvRecord.get("last_name"),
              LocalDate.parse(csvRecord.get("transaction_date"),  DateTimeFormatter.ofPattern("MM/dd/yyyy")),
              Double.parseDouble(csvRecord.get("transaction_amount"))
            );
        tutorials.add(tutorial);
      }

      return tutorials;
    } catch (IOException e) {
      throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
    }
  }

}