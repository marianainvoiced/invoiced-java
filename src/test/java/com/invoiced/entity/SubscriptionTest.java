package com.invoiced.entity;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.Timestamp;

import org.junit.Rule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

public class SubscriptionTest {
	@Rule
	public WireMockRule wireMockRule = new WireMockRule();

	@Test
	public void testParentID() {
		Connection conn = new Connection("", true);
		conn.testModeOn();

		Subscription subscription = conn.newSubscription();
		assertTrue("Invoice Parent Id is incorrect", subscription.getParentID() == -1);
		subscription.setParentID(-4);
		assertTrue("Invoice Parent Id is incorrect", subscription.getParentID() == -1);

	}

	@Test
	public void testCreate() {

		// references connection_rr_17.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Subscription subscription = conn.newSubscription();

		subscription.customer = 15444;
		subscription.plan = "starter";
		subscription.addons = new SubscriptionAddon[1];
		subscription.addons[0] = new SubscriptionAddon();
		subscription.addons[0].catalogItem = "ipad-license";
		subscription.addons[0].quantity = 11;

		try {

			subscription.create();
			assertTrue("Subscription id is incorrect", subscription.id == 595);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testRetrieve() {

		// references connection_rr_18.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Subscription subscription = conn.newSubscription();

		try {
			subscription = subscription.retrieve(595);

			assertTrue("Subscription customer number is incorrect", subscription.customer == 15444);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testSave() {

		// references connection_rr_18.json
		// references connection_rr_19.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Subscription subscription = conn.newSubscription();

		try {
			subscription = subscription.retrieve(595);
			subscription.plan = "pro";

			subscription.save();

			assertTrue("Subscription should have been updated", subscription.plan.equals("pro"));

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testDelete() {

		// references connection_rr_20.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Subscription subscription = conn.newSubscription();

		try {
			subscription = subscription.retrieve(595);
			subscription.cancel();

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testPreviewSubscription() {

		// references connection_rr_75.json

		Connection conn = new Connection("", true);
		conn.testModeOn();

		Subscription subscription = conn.newSubscription();

		subscription.customer = 481594;
		subscription.plan = "starter";

		try {

			SubscriptionPreview preview = subscription.preview();
			assertTrue("Preview MRR is incorrect", preview.mrr == 49);

		} catch (Exception e) {
			fail(e.getMessage());
		}

	}

	@Test
	public void testJsonSerialization() {
		new Subscription(null);

		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			String jsonString = "{\n    \"id\": 595,\n    \"customer\": 15444,\n    \"plan\": \"starter\",\n    \"cycles\": null,\n    \"quantity\": 1,\n    \"start_date\": 1420391704,\n    \"period_start\": 1446657304,\n    \"period_end\": 1449249304,\n    \"cancel_at_period_end\": false,\n    \"canceled_at\": null,\n    \"status\": \"active\",\n    \"addons\": [\n        {\n            \"id\": 3,\n            \"catalog_item\": \"ipad-license\",\n            \"quantity\": 11,\n            \"created_at\": 1420391704\n        }\n    ],\n    \"discounts\": [],\n    \"taxes\": [],\n    \"url\": \"https://dundermifflin.invoiced.com/subscriptions/o2mAd2wWVfYy16XZto7xHwXX\",\n    \"created_at\": 1420391704,\n    \"metadata\": {}\n}";

			Subscription s1 = mapper.readValue(jsonString, Subscription.class);

			assertTrue("Id is incorrect", s1.id == 595L);
			assertTrue("Customer is incorrect", s1.customer == 15444L);
			assertTrue("Plan is incorrect", s1.plan.equals("starter"));
			assertTrue("Cycles is incorrect", s1.cycles == 0);
			assertTrue("Quantity is incorrect", s1.quantity == 1);
			assertTrue("Start Date is incorrect", s1.startDate == 1420391704L);
			assertTrue("Period Start is incorrect", s1.periodStart == 1446657304L);
			assertTrue("Period End is incorrect", s1.periodEnd == 1449249304L);
			assertTrue("Cancel At Period End is incorrect", s1.cancelAtPeriodEnd == false);
			assertTrue("Canceled At incorrect", s1.canceledAt == 0);
			assertTrue("Status is incorrect", s1.status.equals("active"));
			assertTrue("Addons is incorrect", s1.addons.length > 0);
			assertTrue("Discounts is incorrect", s1.discounts.length == 0);
			assertTrue("Taxes is incorrect", s1.taxes.length == 0);
			assertTrue("Url is incorrect",
			           s1.url.equals("https://dundermifflin.invoiced.com/subscriptions/o2mAd2wWVfYy16XZto7xHwXX"));
			assertTrue("Created At is incorrect", s1.createdAt == 1420391704);
			assertTrue("Metadata is incorrect", s1.metadata != null);

			assertTrue("Addon id is incorrect", s1.addons[0].id == 3);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
			fail();
		} catch (JsonMappingException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testJsonDeserialization() {

	}

}