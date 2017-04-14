module nts.uk.pr.view.qpp007.c {
    export module service {
        import OutputSettingDto =  nts.uk.pr.view.qpp007.c.viewmodel.OutputSettingDto;
        import OutputSettingHeader = nts.uk.pr.view.qpp007.c.viewmodel.OutputSettingDto;

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
        export function save(data: OutputSettingDto): JQueryPromise<any> {
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
        export function findOutputSettingDetail(id: string): JQueryPromise<OutputSettingDto> {
            return nts.uk.request.ajax(paths.findOutputSettingDetail + "/" + id);
        }
        /**
         *  Find all outputSettings
         */
        export function findAllOutputSettings(): JQueryPromise<Array<OutputSettingHeader>> {
            return nts.uk.request.ajax(paths.findAllOutputSettings);
        }
        /**
         *  Find all aggregateItems.
         */
        export function findAllAggregateItems(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.findAllAggregateItems);
        }
        /**
         * Find all masterItems.
         */
        export function findAllMasterItems(): JQueryPromise<any> {
            var dfd = $.Deferred<any>();
            // mock data
            var masterItems: any = [];
            for (let i = 1; i < 15; i++) {
                masterItems.push({ code: 'MI' + i, name: '基本給' + i, paymentType: 'Salary', taxDivision: 'Payment' });
            }
            for (let i = 1; i < 15; i++) {
                masterItems.push({ code: 'M0' + i, name: '基本給' + i, paymentType: 'Salary', taxDivision: 'Deduction' });
            }
            return dfd.resolve(masterItems).promise();
        }

    }
}
