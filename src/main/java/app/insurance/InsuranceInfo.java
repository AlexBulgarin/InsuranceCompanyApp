package app.insurance;

import app.Insurant;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InsuranceInfo {
    private Insurance insurance;
    private Insurant insurant;
}
