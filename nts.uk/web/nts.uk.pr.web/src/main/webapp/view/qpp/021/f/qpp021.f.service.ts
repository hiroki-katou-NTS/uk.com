module qpp021.f.service {

    var paths = {
        save: "ctx/pr/report/payment/contact/personalsetting/save"
    };

    /**
     * Save
     */
    export function save(data: any): JQueryPromise<any> {
        return nts.uk.request.ajax(paths.save, data);
    }

}
