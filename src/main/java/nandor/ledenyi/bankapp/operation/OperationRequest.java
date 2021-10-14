package nandor.ledenyi.bankapp.operation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OperationRequest {

    private Long fromAccountId;
    private Long ToAccountId;
    private double amount;
}
