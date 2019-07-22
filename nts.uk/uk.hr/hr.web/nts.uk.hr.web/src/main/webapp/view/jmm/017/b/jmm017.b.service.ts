module nts.uk.hr.view.jmm017.b {
    import ajax = nts.uk.request.ajax;
    import format = nts.uk.text.format;
    export module service {
        var paths: any = {
            getCareerList: "careermgmt/careerpath/getCareerList",
        }

         export function getCareerList(param: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getCareerList, param);
        }
    }
}