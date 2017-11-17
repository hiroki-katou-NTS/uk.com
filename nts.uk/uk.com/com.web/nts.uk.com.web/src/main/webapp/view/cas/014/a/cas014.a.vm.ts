module nts.uk.com.view.cas014.a {
    import getText = nts.uk.resource.getText;
    import confirm = nts.uk.ui.dialog.confirm;
    import alertError = nts.uk.ui.dialog.alertError;
    import info = nts.uk.ui.dialog.info;
    import modal = nts.uk.ui.windows.sub.modal;
    import setShared = nts.uk.ui.windows.setShared;
    import textUK = nts.uk.text;
    import block = nts.uk.ui.block;
    export module viewmodel {
        export class ScreenModel {
            date: KnockoutObservable<string>;

            roleSetList: Array<RoleSet>;
            jobTitleList: Array<JobTitle>;

            roleSetJobTitle: KnockoutObservable<RoleSetJobTitle>;

            constructor() {
                let self = this;
                self.date = ko.observable(new Date().toISOString());

            }

            //startPage(): void {
            startPage(date?: string): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                block.invisible();

                new service.Service().getAllData(self.date()).done(function(data: any) {
                    if (data) {
                        self.roleSetList = [
                            new RoleSet('01', '基本給'),
                            new RoleSet('02', '役職手当'),
                            new RoleSet('03', '役職手当'),
                            new RoleSet('04', '役職手当'),
                            new RoleSet('05', '役職手当'),
                            new RoleSet('06', '基本給')
                        ];

                        self.jobTitleList = [
                            new JobTitle('000001', '01', 'job title 1'),
                            new JobTitle('000002', '02', 'job title 2'),
                            new JobTitle('000003', '03', 'job title 3'),
                            new JobTitle('000004', '04', 'job title 4'),
                            new JobTitle('000005', '05', 'job title 5'),
                            new JobTitle('000006', '06', 'job title 6'),
                            new JobTitle('000007', '07', 'job title 7'),
                            new JobTitle('000008', '08', 'job title 8'),
                            new JobTitle('000009', '09', 'job title 9'),
                        ];

                        self.roleSetJobTitle = ko.observable(new RoleSetJobTitle(false, self.jobTitleList, self.roleSetList));
                    } else {
                        self.roleSetList = [
                            new RoleSet('01', 'Role Set 1'),
                            new RoleSet('02', 'Role Set 2'),
                            new RoleSet('03', 'Role Set 3'),
                            new RoleSet('04', 'Role Set 4'),
                            new RoleSet('05', 'Role Set 5'),
                            new RoleSet('06', 'Role Set 6')
                        ];

                        self.jobTitleList = [
                            new JobTitle('000001', '01', 'job title 1'),
                            new JobTitle('000002', '02', 'job title 2'),
                            new JobTitle('000003', '03', 'job title 3'),
                            new JobTitle('000004', '04', 'job title 4'),
                            new JobTitle('000005', '05', 'job title 5'),
                            new JobTitle('000006', '06', 'job title 6'),
                            new JobTitle('000007', '07', 'job title 7'),
                            new JobTitle('000008', '08', 'job title 8'),
                            new JobTitle('000009', '09', 'job title 9'),
                            new JobTitle('000010', '10', 'job title 10')
                        ];

                        self.roleSetJobTitle = ko.observable(new RoleSetJobTitle(false, self.jobTitleList, self.roleSetList));
                    }
                    $(".fixed-table").ntsFixedTable({ height: 200 });
                    dfd.resolve();
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError("shit happened!");
                    dfd.reject();
                });
                block.clear();

                /*copy from ccg013
                // Get list jobtitle
                service.getAllJobTitle(self.date()).done(function(listJobTitle: Array<viewmodel.TitleMenu>) {
                    listJobTitle = _.orderBy(listJobTitle, ["code"], ["asc"]);
                    _.each(listJobTitle, function(obj: viewmodel.TitleMenu) {
                        self.listJobTitle.push(new TitleMenu(obj.code, obj.name, obj.id, obj.startDate, obj.endDate));
                        self.listJobId.push(obj.id);
                    });
                    // get list for combobox
                    self.getListCombobox();
                    dfd.resolve();
                }).fail(function(error) {
                    nts.uk.ui.dialog.alertError(error.message);
                    dfd.reject();
                });
                */
                //alert('input date: ' + self.date());
                return dfd.promise();
            }

            //            setDefault() {
            //                var self = this;
            //                nts.uk.util.value.reset($("#combo-box, #A_SEL_001"), self.defaultValue() !== '' ? self.defaultValuned);
            //            }

            reloadData() {
                //alert('input date: ' + self.);
            }

            register() {
                let self = this, data: RoleSetJobTitle = ko.toJS(self.roleSetJobTitle), regDetails = [];
                _.each(data.details, d => regDetails.push({ roleSetCd: d.roleSetCd, jobTitleId: d.jobTitleId }));

                let command: any = {
                    applyToConcurrentPerson: data.applyToConcurrentPerson,
                    details: regDetails
                };
                block.invisible();
                //                if (!self.currentData().currentCtgSelected().perInfoCtgName()) {
                //                    return;
                //                }
                //                if (self.isUpdate) {
                //                    let updateCategory = new UpdatePerInfoCtgModel(self.currentData().currentCtgSelected());
                new service.Service().registerData(command).done(function() {
                    info({ messageId: "Msg_15" }).then(() => { block.clear(); });
                }).fail(error => {
                    alertError({ messageId: error.message });
                    block.clear();
                });
                //                } else {
                //                    let newCategory = new AddPerInfoCtgModel(self.currentData().currentCtgSelected());
                //                    new service.Service().addPerInfoCtg(newCategory).done(() => {
                //                        self.reloadData(newCategory.categoryName);
                //                        info({ messageId: "Msg_15" }).then(() => {
                //                            confirm({ messageId: "Msg_213" }).ifYes(() => {
                //                                setShared('categoryId', self.currentData().perInfoCtgSelectCode());
                //                                modal("/view/cps/005/b/index.xhtml").onClosed(() => {
                //                                    let ctgCode = self.currentData().perInfoCtgSelectCode();
                //                                    self.currentData().perInfoCtgSelectCode("");
                //                                    self.currentData().perInfoCtgSelectCode(ctgCode);
                //                                    block.clear();
                //                                });
                //                            }).ifNo(() => {
                //                                block.clear();
                //                                return;
                //                            })
                //                        });
                //                    }).fail(error => {
                //                        alertError({ messageId: error.message });
                //                         block.clear();
                //                    });
                //                }
            }

        }
    }

    export class RoleSet {
        code: string;
        name: string;

        constructor(code: string, name: string) {
            this.code = code;
            this.name = name;
        }
    }

    export class JobTitle {
        id: string;
        code: string;
        name: string;

        constructor(id: string, code: string, name: string) {
            this.id = id;
            this.code = code;
            this.name = name;
        }
    }

    export class RoleSetJobTitleDetail {
        roleSetCd: KnockoutObservable<string>;
        jobTitleId: string;
        roleSetList: Array<RoleSet>;
        jobTitle: JobTitle;

        constructor(jobTitle: JobTitle, roleSetList: Array<RoleSet>) {
            this.roleSetCd = ko.observable(roleSetList[0].code);
            this.jobTitleId = jobTitle.id;
            this.jobTitle = jobTitle;
            this.roleSetList = roleSetList;
        }
    }

    export class RoleSetJobTitle {
        applyToConcurrentPerson: KnockoutObservable<boolean>;
        details: KnockoutObservableArray<RoleSetJobTitleDetail>;

        constructor(applyToConcurrentPerson: boolean, jobTitleList: Array<JobTitle>, roleSetList: Array<RoleSet>) {
            this.applyToConcurrentPerson = ko.observable(applyToConcurrentPerson);
            this.details = ko.observableArray([]);
            _.each(jobTitleList, j => this.details.push(new RoleSetJobTitleDetail(j, roleSetList)));
        }
    }
}

