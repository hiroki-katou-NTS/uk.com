module nts.uk.at.view.kfp001.b {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {

            cScreenmodel: any;
            dScreenmodel: any;
            aggrList: KnockoutObservableArray<any>;

            //Wizard
            stepSelected: KnockoutObservable<NtsWizardStep>;
            activeStep: KnockoutObservable<number>;
            stepList: Array<NtsWizardStep>;

            // List
            columns: KnockoutObservableArray<NtsGridListColumn>;
            currentCode: KnockoutObservable<any>;
            optionalList: KnockoutObservableArray<any>;
            items: KnockoutObservableArray<model.OptionalAggrPeriodDto>;
            currentItem: KnockoutObservable<model.OptionalAggrPeriodDto>

            //
            enableNEW: KnockoutObservable<boolean>;
            enableDEL: KnockoutObservable<boolean>;
            peopleNo: KnockoutObservable<number>;
            constructor() {
                var self = this;
                //import cScreenModel, dScreenModel
                self.cScreenmodel = new nts.uk.at.view.kfp001.c.viewmodel.ScreenModel();
                self.dScreenmodel = new nts.uk.at.view.kfp001.d.viewmodel.ScreenModel();
                self.aggrList = ko.observableArray([]);

                //Init wizard
                self.stepList = [
                    { content: '.step-1' },
                    { content: '.step-2' },
                    { content: '.step-3' }
                ];
                self.activeStep = ko.observable(0);

                self.activeStep.subscribe(newVal => {
                    if (newVal == 0) {
                        $('#hor-scroll-button-hide').hide();
                        _.defer(() => {
                            $('#hor-scroll-button-hide').show();
                        });
                    }
                })

                //
                self.peopleNo = ko.observable(null);
                self.optionalList = ko.observableArray([]);
                self.items = ko.observableArray([]);

                self.columns = ko.observableArray([
                    { headerText: 'コード', key: 'aggrFrameCode', width: 60 },
                    { headerText: '名称', key: 'optionalAggrName', width: 100 }
                ]);
                self.currentItem = ko.observable(new model.OptionalAggrPeriodDto({}));
                self.currentCode = ko.observable();
                self.currentCode.subscribe(function(codeChanged) {
                    if (!nts.uk.text.isNullOrEmpty(codeChanged)) {
                        self.currentItem(self.findOptional(codeChanged));

                    }
                });

                //
                self.enableNEW = ko.observable(true);
                self.enableDEL = ko.observable(true);

                //                self.aggrFrameCode = ko.observable("D01");
                //                self.optionalAggrName = ko.observable("THANH DEP ZAI");
                //                self.startDate = ko.observable('15062018');
                //                self.endDate = ko.observable('20062018');
            }

            start(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                $.when(self.getAllOptionalAggrPeriod()).done(function() {
                    if (self.items().length > 0) {
                        self.currentCode(self.items()[0].aggrFrameCode());

                    } else {
                        self.initDataB();
                    }
                    //                    
                    //                    service.findByAggrFrameCode(self.currentItem().aggrFrameCode()).done(function(aggrPeriod_arr: Array<model.IOptionalAggrPeriodDto>) {
                    //                        if(aggrPeriod_arr){
                    //                          $('#label-hidden').show();  
                    //                        }
                    //                    })
                    self.peopleNo(self.items().length);
                    
                    dfd.resolve();
                }).fail(function() {
                    dfd.reject();
                });
                return dfd.promise();
            }

            getAllOptionalAggrPeriod(): JQueryPromise<any> {
                var self = this;
                var dfd = $.Deferred();

                service.findAllOptionalAggrPeriod().done(function(optionalAggrPeriod_arr: Array<model.IOptionalAggrPeriodDto>) {
                    self.optionalList(optionalAggrPeriod_arr);
                    _.forEach(optionalAggrPeriod_arr, function(optionalAggrPeriodRes: model.IOptionalAggrPeriodDto) {
                        var optionalAggr: model.IOptionalAggrPeriodDto = {
                            aggrFrameCode: optionalAggrPeriodRes.aggrFrameCode,
                            optionalAggrName: optionalAggrPeriodRes.optionalAggrName,
                            startDate: optionalAggrPeriodRes.startDate,
                            endDate: optionalAggrPeriodRes.endDate
                        };
                        self.items.push(new model.OptionalAggrPeriodDto(optionalAggr));
                        $('#code-text-d4-2').focus();
                    });
                    dfd.resolve();
                }).fail(function(error) {
                    alert(error.message);
                    dfd.reject(error);
                });
                return dfd.promise();
            }

            changedCode(value) {
                var self = this;
                self.currentItem(self.findOptional(value));
            }


            findOptional(value: any): any {
                let self = this;
                var result = _.find(self.items(), function(obj: model.OptionalAggrPeriodDto) {
                    return obj.aggrFrameCode() == value;
                });
                return result;
            }
            initDataB() {
                let self = this;
                var emptyObject: model.IOptionalAggrPeriodDto = {};
                self.currentItem(new model.OptionalAggrPeriodDto(emptyObject))
                self.currentCode("");
                $('#code-text-d4-2').focus();
                $('#update-mode').hide();

            }

            opendScreenB() {
                nts.uk.request.jump("/view/kfp/001/b/index.xhtml");
            }

            opendScreenC() {
                let self = this;

                nts.uk.request.jump("/view/kfp/001/c/index.xhtml");
            }

            opendScreenD() {
                let self = this;

                nts.uk.request.jump("/view/kfp/001/d/index.xhtml");
            }

            deleteDataB() {

            }
            navigateView() {
                nts.uk.request.jump("/view/kfp/001/a/index.xhtml");
            }
            opendScreenBorJ() {
                let self = this;
                $("#wizard").ntsWizard("next").done(function() {
                    self.enableNEW(false);
                    self.enableDEL(false);
                    var data = {
                        aggrFrameCode: self.currentItem().aggrFrameCode(),
                        optionalAggrName: self.currentItem().optionalAggrName(),
                        startDate: self.currentItem().startDate(),
                        endDate: self.currentItem().endDate(),
                        peopleNo: self.peopleNo()
                    }
                    nts.uk.ui.windows.setShared("KFP001_DATA", data);
                    self.dScreenmodel.start();
                });
            }


        }

        export module model {
            export interface IOptionalAggrPeriodDto {
                aggrFrameCode?: string;
                optionalAggrName?: string;
                startDate?: number;
                endDate?: number;

            }
            export class OptionalAggrPeriodDto {
                aggrFrameCode: KnockoutObservable<string>;
                optionalAggrName: KnockoutObservable<string>;
                startDate: KnockoutObservable<number>;
                endDate: KnockoutObservable<number>;
                constructor(param: IOptionalAggrPeriodDto) {
                    this.aggrFrameCode = ko.observable(param.aggrFrameCode || null);
                    this.optionalAggrName = ko.observable(param.optionalAggrName || null);
                    this.startDate = ko.observable(param.startDate || null);
                    this.endDate = ko.observable(param.endDate || null);
                }
            }
        }
    }
}
