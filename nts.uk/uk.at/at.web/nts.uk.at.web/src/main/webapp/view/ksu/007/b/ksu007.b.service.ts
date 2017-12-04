module nts.uk.at.view.ksu007.b {

    import ScheduleBatchCorrectSettingSave = nts.uk.at.view.ksu007.a.service.model.ScheduleBatchCorrectSettingSave;
    export module service {
        var paths = {
            executionScheduleBatchCorrectSetting: "ctx/at/schedule/processbatch/execution"
        }


        /**
         * call service execution ScheduleBatchCorrectSetting
         */
        export function executionScheduleBatchCorrectSetting(command: ScheduleBatchCorrectSettingSave): JQueryPromise<any> {
            return nts.uk.request.ajax('at', paths.executionScheduleBatchCorrectSetting, command);
        }
    }
}