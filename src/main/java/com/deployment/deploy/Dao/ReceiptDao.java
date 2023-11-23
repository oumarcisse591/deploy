package com.deployment.deploy.Dao;

import com.deployment.deploy.Entity.Receipt;

import java.util.List;

public interface ReceiptDao {

    List<Receipt> findAllReceipt();

    Receipt findFirstByOrderByUdDesc();
    Receipt findByReceiptName(String userName);

    Long getCount();

    Receipt findReceiptById(int theId);

    void deleteReceiptById(int theId);

    Receipt saveReceipt(Receipt theReceipt);
}
