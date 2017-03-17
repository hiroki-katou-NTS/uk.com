module qpp008.c.service {
    var paths: any = {
        getListComparingFormHeader: "report/payment/comparing/find/formHeader"
    }

    export function getListComparingFormHeader(): JQueryPromise<Array<model.ComparingFormHeader>> {
        let dfd = $.Deferred<Array<model.ComparingFormHeader>>();
        nts.uk.request.ajax(paths.getListComparingFormHeader)
            .done(function(res: Array<model.ComparingFormHeader>) {
                dfd.resolve(res);
            })
            .fail(function(error) {
                dfd.reject(error);
            })
        return dfd.promise();
    }

    export module model {
        export class ComparingFormHeader {
            formCode: string;
            formName: string;

            constructor(formCode: string, formName: string) {
                this.formCode = formCode;
                this.formName = formName;
            }
        }
    }
}