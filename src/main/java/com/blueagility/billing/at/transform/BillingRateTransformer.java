package com.blueagility.billing.at.transform;

import com.blueagility.billing.entity.BillingRate;
import cucumber.api.Transformer;

/**
 * Transforms a string to a billing rate
 */
public class BillingRateTransformer extends Transformer<BillingRate> {

    public BillingRate transform(String value) {
        return new BillingRate(value);
    }

}
