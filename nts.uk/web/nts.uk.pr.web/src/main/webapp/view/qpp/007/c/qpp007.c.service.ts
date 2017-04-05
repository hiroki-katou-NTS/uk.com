module nts.uk.pr.view.qpp007.c {
    export module service {
        /**
         *  Service paths
         */
        var paths: any = {
            save: "ctx/pr/report/salary/outputsetting/save",
            remove: "ctx/pr/report/salary/outputsetting/remove",
            findOutputSettingDetail: "ctx/pr/report/salary/outputsetting/find",
            findAllOutputSettings: "ctx/pr/report/salary/outputsetting/findall",
            findAllAggregateItems: "ctx/pr/report/salary/aggregate/item/findall"
        };

        /**
         *  Update
         */
        export function save(data: any): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.save, data);
        }
        /**
         *  Delete
         */
        export function remove(code: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.remove, {code: code});
        }
        /**
         *  Find outputSetting detail
         */
        export function findOutputSettingDetail(id: string): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findOutputSettingDetail + "/" + id);
        }
        /**
         *  Find all outputSettings
         */
        export function findAllOutputSettings(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findAllOutputSettings);
        }
        /**
         *  Find all aggregateItems.
         */
        export function findAllAggregateItems(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findAllAggregateItems);
        }

    }
}
