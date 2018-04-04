module nts.uk.at.view.kaf018.e {
    import format = nts.uk.text.format;
    export module service {
        var paths: any = {
            getWorkplaceExperienceStartUp: "at/record/realitystatus/getStatusActivity",
        }

        /**
         * アルゴリズム「承認状況職場実績起動」を実行する
         */
        export function getStatusActivity(obj: any): JQueryPromise<any> {
            return nts.uk.request.ajax("at", paths.getWorkplaceExperienceStartUp, obj);
        }
    }
}