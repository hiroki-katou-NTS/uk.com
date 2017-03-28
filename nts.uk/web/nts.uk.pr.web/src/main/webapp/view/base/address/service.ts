module nts.uk.pr.view.base.address {
    export module service {

        var pathService: any = {
            findAddressZipCode: "ctx/pr/core/address/find"
        };

        export function findAddressZipCode(zipCode: string): JQueryPromise<model.AddressSelection[]> {
            return nts.uk.request.ajax(pathService.findAddressZipCode + '/' + zipCode);
        }

        export function findAddressZipCodeToRespone(zipCode: string): JQueryPromise<model.AddressRespone> {
            var dfd = $.Deferred<model.AddressRespone>();
            var addressRespone: model.AddressRespone;
            addressRespone = new model.AddressRespone('0', '', null);
            if (zipCode && zipCode != '') {
                service.findAddressZipCode(zipCode).done(data => {
                    var datalength: number = 0;
                    if (data != null) {
                        datalength = data.length;
                    }
                    if (datalength == 0) {
                        addressRespone = new model.AddressRespone('0', 'ER010', null);
                        dfd.resolve(addressRespone);
                    }
                    if (datalength == 1) {
                        addressRespone = new model.AddressRespone('1', '', data[0]);
                        dfd.resolve(addressRespone);
                    }
                    if (datalength >= 2) {
                        addressRespone = new model.AddressRespone('2', '', null);
                        dfd.resolve(addressRespone);
                    }
                }).fail(function(error) {
                    addressRespone = new model.AddressRespone('0', error.messageId, null);
                    dfd.resolve(addressRespone);
                });
            }
            else {
                addressRespone = new model.AddressRespone('0', 'ER001', null);
                dfd.resolve(addressRespone);
            }
            return dfd.promise();
        }

        export function findAddressZipCodeSelection(zipCode: string): JQueryPromise<model.AddressRespone> {
            var dfd = $.Deferred<model.AddressRespone>();
            var addressRespone: model.AddressRespone;
            addressRespone = new model.AddressRespone('0', '', null);

            nts.uk.ui.windows.setShared('zipCode', zipCode);
            nts.uk.ui.windows.sub.modal("/view/base/address/index.xhtml", { height: 400, width: 530, title: "郵便番号" }).onClosed(() => {
                var zipCodeRes: model.AddressSelection = nts.uk.ui.windows.getShared('zipCodeRes');
                if (zipCodeRes) {
                    addressRespone = new model.AddressRespone('1', '', zipCodeRes);
                    dfd.resolve(addressRespone);
                } else {
                    addressRespone = new model.AddressRespone('0', 'ER010', null);
                    dfd.resolve(addressRespone);
                }
            });
            return dfd.promise();
        }

        export function getinfor(address: model.AddressSelection): string {
            return address.id + ' ' + address.prefecture + ' ' + address.town
                + ' ' + address.prefecture + ' ' + address.zipCode;
        }
        export module model {

            export class AddressSelection {
                id: string;

                /** The city. */
                city: string;

                /** The town. */
                town: string;

                /** The prefecture. */
                prefecture: string;

                /** The zip code. */
                zipCode: string;
            }

            export class AddressRespone {

                errorCode: string; //0 error 010 // error 
                message: string; // message (001)
                address: AddressSelection; // error =1 (size 1)
                constructor(errorCode: string, message: string, address: AddressSelection) {
                    this.errorCode = errorCode;
                    this.message = message;
                    this.address = address;
                }
            }
        }
    }
}