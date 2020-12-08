package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

@JsonFilter("customFilter")
public class Invoice extends AbstractEntity<Invoice> {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("id")
  public long id;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("customer")
  public long customer;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("name")
  public String name;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("number")
  public String number;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("email")
  public String email;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("autopay")
  public Boolean autopay;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("currency")
  public String currency;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("draft")
  public Boolean draft;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("closed")
  public Boolean closed;

  @JsonProperty(value = "paid", access = JsonProperty.Access.WRITE_ONLY)
  public Boolean paid;

  @JsonProperty(value = "status", access = JsonProperty.Access.WRITE_ONLY)
  public String status;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("sent")
  public Boolean sent;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("chase")
  public Boolean chase;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("next_chase_on")
  public long nextChaseOn;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("attempt_count")
  public long attemptCount;

  @JsonProperty(value = "next_payment_attempt", access = JsonProperty.Access.WRITE_ONLY)
  public long nextPaymentAttempt;

  @JsonProperty(value = "subscription", access = JsonProperty.Access.WRITE_ONLY)
  public long subscription;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("date")
  public long date;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("due_date")
  public long dueDate;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("payment_terms")
  public String paymentTerms;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("items")
  public LineItem[] items;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("notes")
  public String notes;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("subtotal")
  public double subtotal;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("discounts")
  public Discount[] discounts;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("taxes")
  public Tax[] taxes;

  @JsonProperty(value = "total", access = JsonProperty.Access.WRITE_ONLY)
  public double total;

  @JsonProperty(value = "balance", access = JsonProperty.Access.WRITE_ONLY)
  public double balance;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("tags")
  public Object[] tags;

  @JsonProperty(value = "url", access = JsonProperty.Access.WRITE_ONLY)
  public String url;

  @JsonProperty(value = "payment_url", access = JsonProperty.Access.WRITE_ONLY)
  public String paymentUrl;

  @JsonProperty(value = "pdf_url", access = JsonProperty.Access.WRITE_ONLY)
  public String pdfUrl;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
  public long createdAt;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("metadata")
  public Object metadata;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("ship_to")
  public Object shipTo;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("attachments")
  public long[] attachments;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("disabled_payment_methods")
  public String[] disabledPaymentMethods;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("calculate_taxes")
  public Boolean calculateTaxes;

  public Invoice(Connection conn) {
    super(conn, Invoice.class);
    this.entityName = "/invoices";
  }

  Invoice() {
    super(Invoice.class);
    this.entityName = "/invoices";
  }

  @Override
  @JsonIgnore
  protected String getEntityId() {
    return String.valueOf(this.id);
  }

  @Override
  @JsonIgnore
  protected String[] getCreateExclusions() {
    return new String[] {
      "id",
      "paid",
      "status",
      "total",
      "url",
      "pdf_url",
      "object",
      "created_at",
      "attempt_count",
      "next_payment_attempt",
      "subscription",
      "subtotal",
      "balance",
      "payment_url"
    };
  }

  @Override
  @JsonIgnore
  protected String[] getSaveExclusions() {
    return new String[] {
      "id",
      "paid",
      "status",
      "total",
      "url",
      "pdf_url",
      "object",
      "created_at",
      "attempt_count",
      "next_payment_attempt",
      "subscription",
      "subtotal",
      "balance",
      "payment_url"
    };
  }

  @JsonIgnore
  public Email[] send(EmailRequest emailRequest) throws EntityException {
    String url = this.getEndpoint(true) + "/emails";

    try {
      String emailRequestJson = emailRequest.toJsonString();

      String response = this.getConnection().post(url, null, emailRequestJson);

      return Util.getMapper().readValue(response, Email[].class);
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }

  @JsonIgnore
  public TextMessage[] sendText(TextRequest textRequest) throws EntityException {
    String url = this.getEndpoint(true) + "/text_messages";

    try {
      String textRequestJson = textRequest.toJsonString();

      String response = this.getConnection().post(url, null, textRequestJson);

      return Util.getMapper().readValue(response, TextMessage[].class);
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }

  @JsonIgnore
  public Letter sendLetter() throws EntityException {
    String url = this.getEndpoint(true) + "/letters";

    try {
      String response = this.getConnection().post(url, null, "{}");

      return Util.getMapper().readValue(response, Letter.class);
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }

  @JsonIgnore
  public void pay() throws EntityException {
    String url = this.getEndpoint(true) + "/pay";

    try {
      String response = this.getConnection().post(url, null, "");

      Invoice invoice = Util.getMapper().readValue(response, Invoice.class);

      setFields(invoice, this);
    } catch (Throwable c) {

      throw new EntityException(c);
    }
  }

  @JsonIgnore
  public Attachment[] listAttachments() throws EntityException {
    String url = this.getEndpoint(true) + "/attachments";

    try {
      String response = this.getConnection().post(url, null, "");

      return Util.getMapper().readValue(response, Attachment[].class);
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }

  @JsonIgnore
  public void voidInvoice() throws EntityException {
    String url = this.getEndpoint(true) + "/void";

    try {
      String response = this.getConnection().post(url, null, "{}");

      Invoice invoice = Util.getMapper().readValue(response, Invoice.class);

      setFields(invoice, this);
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }

  @JsonIgnore
  public Note newNote() {
    Note note = new Note(this.getConnection());
    note.setEndpointBase(this.getEndpoint(true));
    return note;
  }

  @JsonIgnore
  public PaymentPlan newPaymentPlan() {
    PaymentPlan paymentPlan = new PaymentPlan(this.getConnection());
    paymentPlan.setEndpointBase(this.getEndpoint(true));
    return paymentPlan;
  }
}
