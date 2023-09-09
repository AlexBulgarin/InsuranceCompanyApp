package app.insurance;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TravelInsurance extends Insurance {
    public TravelInsurance(int duration) {
        super(duration);
        this.setPricePerMonth(9);
        this.setTotalPrice(duration * getPricePerMonth());
    }
}
