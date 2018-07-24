module nts.uk.at.view.kdp003.b{
    import getText = nts.uk.resource.getText;
    import StampingOutputItemSetDto = nts.uk.at.view.kdp003.b.service.model.StampingOutputItemSetDto;
    export module viewmodel {

        export class ScreenModel {
            
        items: KnockoutObservableArray<ItemModel>;
        columns: KnockoutObservableArray<NtsGridListColumn>;
        currentId: KnockoutObservable<any>;
        stampMode: KnockoutObservable<boolean>;
        stampCode: KnockoutObservable<string>;
        stampName: KnockoutObservable<string>;
        roundingRules: KnockoutObservableArray<any>;
        selectedRuleCode: any;
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
            self.stampMode = ko.observable(true);
            self.roundingRules = ko.observableArray([
            { code: 1, name: '四捨'},
            { code: 0, name: '切り'}
          ]);
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
                     });
                 
                });
        }

            

            /**
             * Start page
             */
            public startPage(): JQueryPromise<any> {
                let _self = this;
                let dfd = $.Deferred<any>();
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
                       
                         }

                    dfd.resolve();
                }).fail((res: any) => {

                    dfd.reject();
                });

                return dfd.promise();
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