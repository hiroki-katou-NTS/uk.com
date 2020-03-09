module nts.uk.at.view.ksu001.jc.viewmodel {
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    import formatById = nts.uk.time.format.byId;
    import alertError = nts.uk.ui.dialog.alertError;
    import getText = nts.uk.resource.getText;

    export class ScreenModel {
        // listWorkType: KnockoutObservableArray<any> = ko.observableArray(nts.uk.ui.windows.container.windows["MAIN_WINDOW"].globalContext.nts.uk.ui._viewModel.content.viewO.listWorkType());
        listWorkType: KnockoutObservableArray<any> = ko.observableArray([]);
        selectedShiftMasterCode: KnockoutObservable<string> = ko.observable('');
        selectedShiftMaster: KnockoutObservable<any> = ko.observable(null);
        
        selectedTab : KnockoutObservable<string> = ko.observable(getShared('dataForJC'))().selectedTab;
        workplaceId : KnockoutObservable<string> = ko.observable(getShared('dataForJC'))().workplaceId;
        textName: KnockoutObservable<string> = ko.observable(getShared('dataForJC').text || null);
        arrTooltip: any[] = [];
        
        source: KnockoutObservableArray<any> = ko.observableArray(getShared('dataForJC').data || []);
        dataSource: KnockoutObservableArray<any> = ko.observableArray([]);
        textDecision: KnockoutObservable<string> = ko.observable(getShared('dataForJC').textDecision);
        listCheckNeededOfWorkTime: any[] = getShared('dataForJC').listCheckNeededOfWorkTime;
        nashi: string = getText("KSU001_98");
           
        constructor() {
            let self = this;

            self.selectedShiftMasterCode.subscribe((newValue) => {
                let shiftMaster = _.find(self.listWorkType(), ['shiftMasterCode', newValue]);
                self.selectedShiftMaster(shiftMaster);
            });
          
            // Binding Screen B Data to Table
            let indexDitMeMay = 0;
            _.forEach(self.source(), (item) => {
                $($("#table-date td")[indexDitMeMay]).html(item.value);
                self.dataSource().push({ index: item.index, value: item.value });
                indexDitMeMay++;
           })
    
            /**
             * handle when click/ctr+click cell table
             * get workTypeName/workTimeName paste to cell
             * push data to dataSource
             */
            $("#table-date td").on('click', function(event) {
                let nameWTypeWTime: string = self.selectedShiftMaster().shiftMasterName;

                if (event.ctrlKey) {
                    $(this.parentElement.children).html(nameWTypeWTime);
                    let arrDate = _.map($(this.parentElement).prev().children(), (x) => { return +x.innerHTML });
                    _.each(arrDate, (date) => {
                        _.remove(self.dataSource(), { index: date });
                        self.dataSource().push({ index: date, value: nameWTypeWTime, data: self.selectedShiftMaster() });
                    });
                } else {
                    $(this).html(nameWTypeWTime);
                    let index = +$(this).parent().prev().children()[$(this).index()].innerHTML;
                    _.remove(self.dataSource(), { index: index });
                    self.dataSource().push({ index: index, value: nameWTypeWTime, data: self.selectedShiftMaster() });
                }
            });
        }

        /**
         * Clear data in table
         */
        clearData(): void {
            let self = this;
            $("#table-date td").html('');
            self.dataSource([]);
        }

        /**
         * decision
         */
        decision(): void {
            let self = this;
            $("#test2").trigger("validate");
            if (nts.uk.ui.errors.hasError()) {
                return;
            }
            if (self.dataSource().length == 0) {
                nts.uk.ui.dialog.alertError({ messageId: "Msg_1591" });
                return;
            }
            
            // sort dataSoucre (tooltip) ASC
            let dataSourceOrder = _.orderBy(self.dataSource(), ['index'], ['asc']);
            let arrTooltip = _.map(dataSourceOrder, (data) => {
                return '[' + data.value + ']';
            });
            let arrTooltipClone = _.clone(arrTooltip);

            for (let i = 7; i < arrTooltipClone.length; i += 7) {
                arrTooltip.splice(i, 0, 'lb');
                i++;
            }

            let tooltip: string = arrTooltip.join('→');
            tooltip = tooltip.replace(/→lb/g, '\n');
            
            // sap xep cho mang lien mach
            let index = 0;
            let arrData = _.map(dataSourceOrder, (dataS) => {
                index++;
                return {
                    index: index,
                    value: dataS.value,
                    data: dataS.data
                };
            });

            setShared("dataFromJB", {
                text: self.textName(),
                tooltip: tooltip,
                data: arrData
            });
            
            nts.uk.ui.windows.close();
        }

        /**
         * Close dialog
         */
        closeDialog(): void {
            nts.uk.ui.windows.close();
        }
        
        /** Query */
        initShiftWork() {
            let self = this;

            let taisho = {
                targetUnit: 0,
                workplaceId: '',
                workplaceGroupId: ''
            }
            if (self.selectedTab == 'company') {
                taisho.targetUnit = null;
                taisho.workplaceId = null;
                taisho.workplaceGroupId = null; 
            }
            if (self.selectedTab == 'workplace') {
                taisho.workplaceId = self.workplaceId;
                taisho.targetUnit = 0
            }
            if (self.selectedTab == 'groupworkplace') {
                taisho.workplaceId = '';
            }
            
            service.getShiftMasterWorkInfo(taisho).done((data) => {
                data.unshift({shiftMasterName: nts.uk.resource.getText("KSU001_98"), shiftMasterCode : "", workTime1 : "",workTime2 : "", remark : ""});
                 if (data) {
                    for (let i= 0; i < data.length; i++){
                    data[i].workTime1 = data[i].workTime1 + " " + data[i].workTime2;
                    }
                   }
                self.listWorkType(_.sortBy(data, ['shiftMasterCode'])); 
            }).fail((res: any) => {
                nts.uk.ui.dialog.alert({ messageId: res.messageId });
            });
        }
       
    }
}