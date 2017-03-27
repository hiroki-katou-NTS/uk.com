module nts.uk.pr.view.base.address {
    export module service {

        var pathService: any = {
            findAddressZipCode: "ctx/pr/core/address/find"
        };

        export function findAddressZipCode(zipCode: string): JQueryPromise<Array<model.AddressSelection>> {
            return nts.uk.request.ajax(pathService.findAddressZipCode + '/' + zipCode);
        }

        export function getinfor(address: model.AddressSelection): string {
            return address.id + address.prefecture + address.town + address.prefecture + address.zipCode;
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
        }
    }
}