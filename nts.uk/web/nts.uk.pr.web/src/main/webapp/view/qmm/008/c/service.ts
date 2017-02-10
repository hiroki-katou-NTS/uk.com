module nts.uk.pr.view.qmm008.c {

    export module service {

        // Service paths.
        var servicePath = {
            getOfficeDetailData: "pr/insurance/social/find"
        };

        /**
         * Function is used to load office detail by office code
         */
        export function getOfficeItemDetail(code: string): JQueryPromise<model.finder.OfficeItemDto> {
            // Init new dfd.
            var dfd = $.Deferred<model.finder.OfficeItemDto>();
            var findPath = servicePath.getOfficeDetailData + "/" + code;
            // Call ajax.
            nts.uk.request.ajax(findPath).done(function(data: model.finder.OfficeItemDto) {
                // Resolve.
                dfd.resolve(data);
            });
            // Ret promise.
            return dfd.promise();
        }

        /**
        * Model namespace.
        */
        export module model {
            export module finder {
                export class ChooseOption {
                    code: string;
                    name: string;
                }
                //TODO change office item field
                export class OfficeItemDto {
                    companyCode: string;
                    code: string;
                    name: string;
                    shortName: string;
                    picName: string;
                    picPosition: string;
                    potalCode: string;
                    prefecture: string;
                    address1st: string;
                    address2nd: string;
                    kanaAddress1st: string;
                    kanaAddress2nd: string;
                    phoneNumber: string;
                    healthInsuOfficeRefCode1st: string;
                    healthInsuOfficeRefCode2nd: string;
                    pensionOfficeRefCode1st: string;
                    pensionOfficeRefCode2nd: string;
                    welfarePensionFundCode: string;
                    officePensionFundCode: string;
                    healthInsuCityCode: string;
                    healthInsuOfficeSign: string;
                    pensionCityCode: string;
                    pensionOfficeSign: string;
                    healthInsuOfficeCode: string;
                    healthInsuAssoCode: string;
                    memo: string;
                }
            }
        }

    }
}
