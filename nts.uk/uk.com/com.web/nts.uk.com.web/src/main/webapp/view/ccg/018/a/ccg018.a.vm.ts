module ccg018.a.viewmodel {
    export class ScreenModel {
        date: KnockoutObservable<string>;
        items: KnockoutObservableArray<TopPageJobSet>;
        columns2: KnockoutObservableArray<NtsGridListColumn>;
        switchOptions: KnockoutObservableArray<any>;
        currentCode: KnockoutObservable<any>;

        constructor() {
            var self = this;
            self.date = ko.observable(new Date().toISOString());
            self.items = ko.observableArray([]);

            for (let i = 1; i < 10; i++) {
                self.items.push(new TopPageJobSet('00' + i, '基本給', "日別実績の修正(明細)" + i, "未設定" + i, i % 2, '0000' + i, 4));
            }
            self.columns2 = ko.observableArray([
                { headerText: nts.uk.resource.getText("CCG018_8"), key: 'code', width: 50, enable: true },
                { headerText: nts.uk.resource.getText("CCG018_9"), key: 'name', width: 120 },
                { headerText: nts.uk.resource.getText("CCG018_10"), key: 'afterLogin', width: 190 },
                { headerText: nts.uk.resource.getText("CCG018_11"), key: 'asTopPage', width: 190 },
                { headerText: nts.uk.resource.getText("CCG018_12"), key: 'personPermissionSet', width: 186, controlType: 'switch' }
            ]);
            self.switchOptions = ko.observableArray([
                { code: 0, name: nts.uk.resource.getText("CCG018_13") },
                { code: 1, name: nts.uk.resource.getText("CCG018_14") }
            ]);
            
            nts.uk.ui.windows.getShared('divideOrNot');
            
            self.currentCode = ko.observable();
            $.when(self.findByCId()).done(function(data) { }).fail();
        }

        /**
         * Find data base on companyId
         */
        findByCId(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            ccg018.a.service.findByCId()
                .done(function(data) {
                    if (!(!!data)) {
                        self.openDialogC();
                    }
                    dfd.resolve();
                }).fail();
            return dfd.promise();
        }

        /**
         * Update/insert data in TOPPAGE_JOB_SET
         */
        update(): void {
            var self = this;
            ccg018.a.service.update(self.items())
                .done(function() {
                }).fail();
        }

        /**
         * Open dialog C
         */
        openDialogC(): void {
            nts.uk.ui.windows.sub.modal("/view/ccg/018/c/index.xhtml", { dialogClass: "no-close" });
        }
    }
    class TopPageJobSet {
        code: string;
        name: string;
        afterLogin: string;
        asTopPage: string;
        personPermissionSet: number;
        jobId: string;
        system: number;
        constructor(code: string, name: string, afterLogin: string, asTopPage: string, personPermissionSet: number, jobId: string, system: number) {
            this.code = code;
            this.name = name;
            this.afterLogin = afterLogin;
            this.asTopPage = asTopPage;
            this.personPermissionSet = personPermissionSet;
            this.jobId = jobId;
            this.system = system;
        }
    }
}