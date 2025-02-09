package com.invoiced.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.invoiced.exception.EntityException;
import com.invoiced.util.Util;

@JsonFilter("customFilter")
public final class Subscription extends AbstractEntity<Subscription> {

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("id")
  public Long id;

  @JsonProperty(value = "object", access = JsonProperty.Access.WRITE_ONLY)
  public String object;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("customer")
  public Long customer;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("amount")
  public Double amount;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("plan")
  public String plan;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("start_date")
  public Long startDate;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("bill_in")
  public String billIn;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("bill_in_advance_days")
  public Long billInAdvanceDays;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("quantity")
  public Long quantity;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("cycles")
  public Long cycles;

  @JsonProperty(value = "period_start", access = JsonProperty.Access.WRITE_ONLY)
  public Long periodStart;

  @JsonProperty(value = "period_end", access = JsonProperty.Access.WRITE_ONLY)
  public Long periodEnd;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("snap_to_nth_day")
  public Long snapToNthDay;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("cancel_at_period_end")
  public Boolean cancelAtPeriodEnd;

  @JsonProperty(value = "canceled_at", access = JsonProperty.Access.WRITE_ONLY)
  public Long canceledAt;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("prorate")
  public Boolean prorate;

  @JsonInclude(JsonInclude.Include.NON_DEFAULT)
  @JsonProperty("proration_date")
  public Long prorationDate;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("paused")
  public Boolean paused;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("contract_period_start")
  public Long contractPeriodStart;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("contract_period_end")
  public Long contractPeriodEnd;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("contract_renewal_cycles")
  public Long contractRenewalCycles;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("contract_renewal_mode")
  public String contractRenewalMode;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("status")
  public String status;

  @JsonProperty(value = "recurring_total", access = JsonProperty.Access.WRITE_ONLY)
  public Long recurringTotal;

  @JsonProperty(value = "mrr", access = JsonProperty.Access.WRITE_ONLY)
  public Long mrr;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("addons")
  public SubscriptionAddon[] addons;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("discounts")
  public Coupon[] discounts;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("taxes")
  public TaxRate[] taxes;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("ship_to")
  public ShippingDetail shipTo;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("pending_line_items")
  public String[] pendingLineItems;

  @JsonProperty(value = "url", access = JsonProperty.Access.WRITE_ONLY)
  public String url;

  @JsonProperty(value = "created_at", access = JsonProperty.Access.WRITE_ONLY)
  public Long createdAt;

  @JsonProperty(value = "updated_at", access = JsonProperty.Access.WRITE_ONLY)
  public Long updatedAt;

  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @JsonProperty("metadata")
  public Object metadata;

  public Subscription(Connection conn) {
    super(conn, Subscription.class);
    this.entityName = "/subscriptions";
  }

  Subscription() {
    super(Subscription.class);
    this.entityName = "/subscriptions";
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
      "period_start",
      "period_end",
      "canceled_at",
      "prorate",
      "proration_date",
      "contract_period_start",
      "contract_period_end",
      "status",
      "recurring_total",
      "mrr",
      "url",
      "created_at"
    };
  }

  @Override
  @JsonIgnore
  protected String[] getSaveExclusions() {
    return new String[] {
      "id",
      "customer",
      "bill_in",
      "cycles",
      "snap_to_nth_day",
      "period_start",
      "period_end",
      "canceled_at",
      "contract_period_start",
      "contract_period_end",
      "status",
      "recurring_total",
      "mrr",
      "url",
      "created_at"
    };
  }

  public void cancel() throws EntityException {
    this.deleteWithResponse();
  }

  public SubscriptionPreview preview() throws EntityException {
    String url = "/subscriptions/preview";

    try {
      String previewJson = this.toJsonString();

      String response = this.getConnection().post(url, null, previewJson);

      return Util.getMapper().readValue(response, SubscriptionPreview.class);
    } catch (Throwable c) {
      throw new EntityException(c);
    }
  }
}
