module nts.uk.at.view.ksm013.a {
    // Import
    export module viewmodel {

        export class ScreenModel {

            columns: KnockoutObservableArray<any>;
            selectedCode: KnockoutObservable<string>;
            lstNurseCl: KnockoutObservableArray<NurseClassification> = ko.observableArray([]);
            nurseClModel: NurseClassificationModel;
            isEditting: KnockoutObservable<boolean>;
            lstLicense: KnockoutObservableArray<any>;
            //selectedLicense: KnockoutObservable<number>;


            constructor() {
                var self = this;
                self.columns = ko.observableArray([
                    { headerText: nts.uk.resource.getText('KSM013_5'), key: 'code', formatter: _.escape, width: 50 },
                    { headerText: nts.uk.resource.getText('KSM013_6'), key: 'name', formatter: _.escape, width: 190 }
                ]);
                self.nurseClModel = new NurseClassificationModel("", "", null, false);
                self.isEditting = ko.observable(false);
                self.selectedCode = ko.observable('');
                self.selectedCode.subscribe(function(codeChanged: string) {
                    let dfd = $.Deferred();
                    if (_.isEmpty(codeChanged)) {
                        self.newCreate();
                        dfd.resolve();
                    } else {
                        service.findDetail(codeChanged).done((dataDetail: NurseDetailClassification) => {
                            self.isEditting(true);
                            self.nurseClModel.createDataModel(dataDetail);
                            self.clearErrorAll();
                            dfd.resolve();
                                 $('#nurseClassificationName').focus();
                        })
                    }
                    return dfd.promise();
                });

                self.lstLicense = ko.observableArray([
                    { licenseCode: '0', licenseName: nts.uk.resource.getText('Enum_LicenseClassification_NURSE') },
                    { licenseCode: '1', licenseName: nts.uk.resource.getText('Enum_LicenseClassification_NURSE_ASSOCIATE') },
                    { licenseCode: '2', licenseName: nts.uk.resource.getText('Enum_LicenseClassification_NURSE_ASSIST') }
                ]);
                //self.selectedLicense = ko.observable(0);
            }

            public startPage(): JQueryPromise<any> {
                let self = this,
                    dfd = $.Deferred();
                nts.uk.ui.block.grayout();
                service.findAll().done((dataAll: Array<NurseClassification>) => {
                    if (!_.isEmpty(dataAll)) {
                        self.lstNurseCl(_.sortBy(dataAll, [function(o) { return o.code; }]));
                        self.selectedCode(self.lstNurseCl()[0].code);
                         
                    } else {
                        self.isEditting(false);
                        $('[tabindex= 5]').focus();
                        self.nurseClModel.resetModel();
                    }
                    nts.uk.ui.block.clear();
                    dfd.resolve();
                })

                return dfd.promise();
            }

            public newCreate() {
                let self = this;
                self.isEditting(false); 
                self.nurseClModel.resetModel();
                self.clearErrorAll();
           }

            public register() {
                let self = this;
                self.nurseClModel.nurseClassificationName($.trim(self.nurseClModel.nurseClassificationName()));
                if (self.validateAll()) {
                    return;
                };
                nts.uk.ui.block.grayout();
                if (!self.isEditting()) {
                    // register
                    service.register(ko.toJSON(self.nurseClModel)).done(() => {
                        service.findAll().done((dataAll: Array<NurseClassification>) => {
                            self.lstNurseCl(_.sortBy(dataAll, [function(o) { return o.code; }]));
                            self.selectedCode(self.nurseClModel.nurseClassificationCode());
                            nts.uk.ui.block.clear();
                            nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                            self.isEditting(true);
                            $('#nurseClassificationName').focus();
                        })
                    }).fail((res) => {
                            nts.uk.ui.block.clear();
                            if (res.messageId == "Msg_3") {
                                $('#nurseClassificationCode').ntsError('set', { messageId: "Msg_3" });
                            }
                    });
                } else {
                    // update
                    service.update(ko.toJSON(self.nurseClModel)).done(() => {
                        self.lstNurseCl(_.map(self.lstNurseCl(), function(el: NurseClassification) {
                            if (el.code == self.nurseClModel.nurseClassificationCode()) {
                                return new NurseClassification(self.nurseClModel.nurseClassificationCode(), self.nurseClModel.nurseClassificationName());
                            }
                            return el;

                        }));
                        nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                        nts.uk.ui.block.clear();
                        $('#nurseClassificationName').focus();
                        self.isEditting(true);
                    });
                }
            }

            public remove() {
                let self = this;
                nts.uk.ui.dialog.confirm({ messageId: 'Msg_18' }).ifYes(function() {
                    let param = {
                        nurseClassificationCode: self.nurseClModel.nurseClassificationCode()
                    }
                    nts.uk.ui.block.grayout();
                    service.remove(param).done(() => {
                        nts.uk.ui.dialog.info({ messageId: "Msg_16" }).then(function(){
                            if (self.lstNurseCl().length == 1) {
                                self.lstNurseCl([]);
                                self.selectedCode("");
                            } else {
                                let indexSelected: number;
                                for (let index: number = 0; index < self.lstNurseCl().length; index++) {
                                    if (self.lstNurseCl()[index].code == self.selectedCode()) {
                                        indexSelected = (index == self.lstNurseCl().length - 1) ? index - 1 : index;
                                        self.lstNurseCl().splice(index, 1);
                                        break;
                                    }
                                }
                                self.selectedCode(self.lstNurseCl()[indexSelected].code);
                               
                            }
                        });
                    }).always(function() {
                        nts.uk.ui.block.clear();
                    });
                }).ifNo(function() {
                });
            }

            private validateAll(): boolean {
                $('#nurseClassificationCode').ntsEditor('validate');
                $('#nurseClassificationName').ntsEditor('validate');
                $('#license').ntsEditor('validate');

                if (nts.uk.ui.errors.hasError()) {
                    return true;
                }
                return false;
            }

            private clearErrorAll(): void {
                $('#nurseClassificationCode').ntsError('clear');
                $('#nurseClassificationName').ntsError('clear');
                $('#license').ntsError('clear');
            }

        }


        export class NurseClassificationModel {
            nurseClassificationCode: KnockoutObservable<string>;
            nurseClassificationName: KnockoutObservable<string>;
            license: KnockoutObservable<number>;
            officeWorker: KnockoutObservable<boolean>;
            constructor(nurseClassificationCode: string, nurseClassificationName: string, license: number, officeWorker: boolean) {
                let self = this;
                self.nurseClassificationCode = ko.observable(nurseClassificationCode);
                self.nurseClassificationName = ko.observable(nurseClassificationName);
                self.license = ko.observable(license);
                self.officeWorker = ko.observable(officeWorker);
                self.license.subscribe(function(codeChanged: any) {
                    if (codeChanged != 2) self.officeWorker(false);
                });
                self.nurseClassificationName.subscribe(function(codeChanged: string) {
                     self.nurseClassificationName($.trim(self.nurseClassificationName()));
                });
            }

            public createDataModel(data: NurseDetailClassification) {
                let self = this;
                self.nurseClassificationCode(data.code);
                self.nurseClassificationName(data.name);
                self.license(data.license);
                self.officeWorker(data.officeWorker);
                $('#nurseClassificationCode').focus();
             
            }

            public resetModel() {
                let self = this;
                self.nurseClassificationCode("");
                self.nurseClassificationName("");
                self.license(0);
                self.officeWorker(false);
                $('#nurseClassificationCode').focus();
            }
        }

        export class NurseDetailClassification {

            /**
             * 看護区分コード
             */
            nurseClassificationCode: string;

            /**
             * 看護区分名称   
             */
            nurseClassificationName: string;

            /**
             *  免許区分    
             */
            license: number;

            /**
             *  事務的業務従事者か
             */
            officeWorker: boolean;

            constructor(nurseClassificationCode: string, nurseClassificationName: string, license: number, officeWorker: boolean) {
                this.nurseClassificationCode = nurseClassificationCode;
                this.nurseClassificationName = nurseClassificationName;
                this.license = license;
                this.officeWorker = officeWorker;
            }
        }

        export class NurseClassification {

            /**
             * 看護区分コード
             */
            code: string;

            /**
             * 看護区分名称   
             */
            name: string;

            constructor(nurseClassificationCode: string, nurseClassificationName: string) {
                this.code = nurseClassificationCode;
                this.name = nurseClassificationName;

            }
        }
    }
}