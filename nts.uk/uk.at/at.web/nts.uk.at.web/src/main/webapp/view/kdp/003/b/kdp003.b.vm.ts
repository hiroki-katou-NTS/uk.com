module nts.uk.at.view.kdp003.b{
    import getText = nts.uk.resource.getText;
    import StampingOutputItemSetDto = nts.uk.at.view.kdp003.b.service.model.StampingOutputItemSetDto;
    export module viewmodel {

        export class ScreenModel {
            
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentId: KnockoutObservable<any>;
        stampMode: KnockoutObservable<boolean>;
        enableDelete: KnockoutObservable<boolean>;
        stampCode: KnockoutObservable<string>;
        stampName: KnockoutObservable<string>;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
        selectMode: boolean;
        selectedOutputEmbossMethod: KnockoutObservable<number>;
        selectedOutputWorkHours: KnockoutObservable<number>;
        selectedOutputSetLocation: KnockoutObservable<number>;
        selectedOutputPosInfor: KnockoutObservable<number>;
        selectedOutputOT: KnockoutObservable<number>;
        selectedOutputNightTime: KnockoutObservable<number>;
        selectedOutputSupportCard: KnockoutObservable<number>;

        constructor() {
             var self = this;
            self.items = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: getText('KDP003_17'), key: 'stampOutputSetCode', width: 110 },
                { headerText: getText('KDP003_18'), key: 'stampOutputSetName', width: 170 },
            ]);
            self.currentId = ko.observable('');
            self.stampCode = ko.observable('');
            self.stampName = ko.observable('');
            self.stampMode = ko.observable(false);
            self.enableDelete = ko.observable(true);
            self.selectMode = false;
            self.roundingRules = ko.observableArray([])
          
            self.selectedOutputEmbossMethod = ko.observable(0);
            self.selectedOutputWorkHours = ko.observable(0);
            self.selectedOutputSetLocation = ko.observable(0);
            self.selectedOutputPosInfor = ko.observable(0);
            self.selectedOutputOT = ko.observable(0);
            self.selectedOutputNightTime = ko.observable(0);
            self.selectedOutputSupportCard = ko.observable(0);
            
             self.currentId.subscribe(newValue => {
                 service.findAll().done((data: Array<StampingOutputItemSetDto>) => {
                     var itemstamOutput = _.find(data, item => item.stampOutputSetCode == newValue);
                           self.stampCode(itemstamOutput.stampOutputSetCode);
                           self.stampName(itemstamOutput.stampOutputSetName);
                           itemstamOutput.outputEmbossMethod? self.selectedOutputEmbossMethod(1):self.selectedOutputEmbossMethod(0);
                           itemstamOutput.outputWorkHours? self.selectedOutputWorkHours(1):self.selectedOutputWorkHours(0);
                           itemstamOutput.outputSetLocation? self.selectedOutputSetLocation(1):self.selectedOutputSetLocation(0);
                           itemstamOutput.outputPosInfor? self.selectedOutputPosInfor(1):self.selectedOutputPosInfor(0);
                           itemstamOutput.outputOT? self.selectedOutputOT(1):self.selectedOutputOT(0);
                           itemstamOutput.outputNightTime? self.selectedOutputNightTime(1):self.selectedOutputNightTime(0);
                           itemstamOutput.outputSupportCard? self.selectedOutputSupportCard(1):self.selectedOutputSupportCard(0);
                           $("#stampName").focus();
                     });
                 
                });
        }

            

            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
                service.getAllEnum().done(data => {
                    data.settingSegment.forEach((item: any, index: number) => {
                    _self.roundingRules().push({ code: item.value, name: item.localizedName });
                });
                    
                });
                 service.findAll().done((data: Array<StampingOutputItemSetDto>) => {
                     if (!_.isEmpty(data)) {
                          _self.items(data);
                           _self.stampMode(false);
                           var itemstamOutput = _.find(data, item => item.stampOutputSetCode == _self.currentId());
                            if (_.isEmpty(itemstamOutput)) {
                                itemstamOutput = data[0];
                            }
                           _self.stampCode(itemstamOutput.stampOutputSetCode);
                           _self.stampName(itemstamOutput.stampOutputSetName);
                         
                           itemstamOutput.outputEmbossMethod? _self.selectedOutputEmbossMethod(1):_self.selectedOutputEmbossMethod(0);
                           itemstamOutput.outputWorkHours? _self.selectedOutputWorkHours(1):_self.selectedOutputWorkHours(0);
                           itemstamOutput.outputSetLocation? _self.selectedOutputSetLocation(1):_self.selectedOutputSetLocation(0);
                           itemstamOutput.outputPosInfor? _self.selectedOutputPosInfor(1):_self.selectedOutputPosInfor(0);
                           itemstamOutput.outputOT? _self.selectedOutputOT(1):_self.selectedOutputOT(0);
                           itemstamOutput.outputNightTime? _self.selectedOutputNightTime(1):_self.selectedOutputNightTime(0);
                           itemstamOutput.outputSupportCard? _self.selectedOutputSupportCard(1):_self.selectedOutputSupportCard(0);
                           $("#stampName").focus();
                       
                         }else{
                         _self.btnNew();
                     }

                    dfd.resolve();
                }).fail((res: any) => {
                    dfd.reject();
                });

                return dfd.promise();
            }
            
            public btnNew(){
                $("#stampCode").focus();
                let self = this;
                self.enableDelete(false);
                self.selectedOutputEmbossMethod(0);
                self.selectedOutputWorkHours(0);
                self.selectedOutputSetLocation(0);
                self.selectedOutputPosInfor(0);
                self.selectedOutputOT(0);
                self.selectedOutputNightTime(0);
                self.selectedOutputSupportCard(0);
                self.stampCode('');
                self.stampName('');
                self.stampMode(true);
                self.selectMode = true;
            }
            
            /**
            * Set focus
            */
            public setInitialFocus(): void {
                let self = this;

                if (_.isEmpty(self.items())) {
                    $('#stampCode').focus();
                } else {
                    $('#stampName').focus();
                }
            }
            
            public register(){
                let self = this;
                let data: StampingOutputItemSetDto = {
                    stampOutputSetCode: self.stampCode(),
                    stampOutputSetName: self.stampName(),
                    outputEmbossMethod: self.selectedOutputEmbossMethod() == 1 ? true :false,
                    outputWorkHours: self.selectedOutputWorkHours() == 1 ? true :false,
                    outputSetLocation: self.selectedOutputSetLocation() == 1 ? true :false,
                    outputPosInfor: self.selectedOutputPosInfor() == 1 ? true :false,
                    outputOT: self.selectedOutputOT() == 1 ? true :false,
                    outputNightTime: self.selectedOutputNightTime() == 1 ? true :false,
                    outputSupportCard: self.selectedOutputSupportCard() == 1 ? true :false
                }
                if(self.selectMode){
                 service.addStampingOutputItemSet(data).done(() => {
                    var objItem = new ItemModel(self.stampCode(),self.stampName());
                    self.items().push(objItem);
                     self.currentId(self.stampCode());
                     nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                });
                }else{
                     service.updateStampingOutputItemSet(data).done(() => {
                          var oldItem = _.find(self.items(), item => item.stampOutputSetCode == self.stampCode());
                          var newItem = new ItemModel(self.stampCode(),self.stampName());
                         self.items.replace( oldItem, newItem )     
                     nts.uk.ui.dialog.info({ messageId: "Msg_15" });
                });
                    
                    }
            
            }
            
            public btnDelete() {
                let self = this;
                var currentIndex: number = null;
                var nextCode: string = null;
                var obj = _.last(self.items());
                currentIndex = self.items.indexOf(obj);
                nts.uk.ui.dialog.confirm({ messageId: "Msg_18" }).ifYes(() => {

                    service.deleteStampingOutputItemSet({ code: self.stampCode() }).done((data: any) => {
                        if (self.items().length == 0) {
                            self.btnNew();
                        } else {

                            if (obj.stampOutputSetCode == self.stampCode()) {
                                if (currentIndex != 0) {
                                    nextCode = self.items()[currentIndex - 1].stampOutputSetCode;
                                    self.currentId(nextCode);
                                }
                            } else {
                                var nextIndex = self.items.indexOf(_.find(self.items(), item => item.stampOutputSetCode == self.stampCode()));
                                if (currentIndex != 0) {
                                    nextCode = self.items()[nextIndex + 1].stampOutputSetCode;
                                    self.currentId(nextCode);
                                }
                            }
                        }
                        service.findAll().done((data: Array<StampingOutputItemSetDto>) => {
                            if (!_.isEmpty(data)) {
                                self.items(data);
                            }
                        });
                        if(currentIndex == 0){
                            self.items([]);
                            self.btnNew();
                        }

                        nts.uk.ui.dialog.info({ messageId: "Msg_16" });
                    });
                }).ifNo(() => {
                    return;
                });

            }

        }
    }
    
     class ItemModel {
        stampOutputSetCode: string;
        stampOutputSetName: string;
        constructor(stampOutputSetCode: string, stampOutputSetName: string) {
            this.stampOutputSetCode = stampOutputSetCode;
            this.stampOutputSetName = stampOutputSetName;
        }
    }
}