module nts.uk.pr.view.base.address {

    import AddressSelection = service.model.AddressSelection;

    export module viewmodel {
        export class ScreenModel {

            addressList: KnockoutObservableArray<AddressSelection>;
            zipCode: KnockoutObservable<string>;

            constructor() {
                var self = this;
                self.addressList = ko.observableArray<AddressSelection>([]);
                self.zipCode = ko.observable('');
                var strZipCode: string = nts.uk.ui.windows.getShared('zipCode');
                service.findAddressZipCode(strZipCode).done(data => {
                    self.addressList(data);
                }).fail(function(error) {
                    console.log(error)
                });
            }

            private chooseZipCode() {
                var self = this;
                for (var itemZipCode of self.addressList()) {
                    if (itemZipCode.id == self.zipCode()) {
                        nts.uk.ui.windows.setShared('zipCodeRes', itemZipCode);
                        break;
                    }
                }
                nts.uk.ui.windows.close();
            }

        }
    }
}