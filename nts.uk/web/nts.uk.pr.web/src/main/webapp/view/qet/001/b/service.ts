module qet001.b {
    export module service {
        import WageLedgerOutputSetting = qet001.a.service.model.WageLedgerOutputSetting;

        // Service paths.
        var servicePath = {
            findOutputSettings: 'ctx/pr/report/wageledger/outputsetting/findAll',
            findOutputSettingDetail: 'ctx/pr/report/wageledger/outputsetting/find'
        }
        
        /**
         * Find output setting detail.
         */
        export function findOutputSettingDetail(settingCode: string): JQueryPromise<WageLedgerOutputSetting> {
            return nts.uk.request.ajax(servicePath.findOutputSettingDetail + '/' + settingCode);
        }
        
    }
}
