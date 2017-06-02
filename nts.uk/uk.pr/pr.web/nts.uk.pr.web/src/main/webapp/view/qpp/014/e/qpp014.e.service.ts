module qpp014.e.service {
    var paths: any = {
        addBankTransfer: "pr/proto/payment/banktransfer/add",
        removeBankTransfer: "pr/proto/payment/banktransfer/remove"
    }

    /**
     * insert data into DB BANK_TRANSFER
     */
    export function addBankTransfer(command: any): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.addBankTransfer, command);
    }
    
     export function removeBankTransfer(command: any): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.removeBankTransfer, command);
    }
}






