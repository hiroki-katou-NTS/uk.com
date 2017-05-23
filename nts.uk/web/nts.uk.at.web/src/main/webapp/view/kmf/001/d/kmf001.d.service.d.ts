declare module nts.uk.pr.view.kmf001 {
    module service {
        class Service {
            constructor();
        }
        function findRetentionYearly(): JQueryPromise<model.RetentionYearlyModel>;
        function saveRetentionYearly(retentionYearlyModel: model.RetentionYearlyModel): JQueryPromise<void>;
        module model {
            class RetentionYearlyModel {
                retentionYearsAmount: number;
                maxDaysCumulation: number;
            }
        }
    }
}
