module nts.uk.pr.view.qmm020.m.viewmodel {
    import model = qmm020.share.model;
    import close = nts.uk.ui.windows.close;
    import getText = nts.uk.resource.getText;
    import setShared = nts.uk.ui.windows.setShared;
    import getShared = nts.uk.ui.windows.getShared;
    export class ScreenModel {
        items: KnockoutObservableArray<StatementDto> = ko.observableArray([]);
        columns: KnockoutObservableArray<any>;
        currentCodeList: KnockoutObservable<StatementDto> = ko.observable();
        params :KnockoutObservableArray<any> = ko.observable();
        constructor(){
            let self = this;
            this.columns = ko.observableArray([
                { headerText: getText('QMM020_26'), prop: 'statementCode', width: 40, formatter: _.escape },
                { headerText: getText('QMM020_27'),  prop: 'statementName', width: 180 , formatter: _.escape},
                { headerText: getText('QMM020_81'), prop: 'displayYearMonth', width: 160, formatter: _.escape }
            ]);
            self.initScreen();
        }

        initScreen(): JQueryPromise<any>{
            let dfd = $.Deferred();
            let self = this;
            let params = getShared(model.PARAMETERS_SCREEN_M.INPUT);
            if(params == null || params == undefined){
                return;
            }
            this.params(params);
            nts.uk.pr.view.qmm020.m.service.getDataStatement(this.params().startYearMonth).done((data)=>{
                if(data && data.length > 0){
                    this.items(StatementDto.fromApp(data));
                    self.currentCodeList(params.statementCode != null && params.statementCode != '' ? params.statementCode : self.items()[0].statementCode);
                }
                dfd.resolve();
            }).fail((err) =>{
                dfd.reject();
                if(err)
                    dialog.alertError(err);
            });
            return dfd.promise();
        }

        submit(){
            let self = this;
            let data :any = {
                statementCode : self.currentCodeList() ,
                statementName : _.find(self.items(), function(item) { return item.statementCode == self.currentCodeList() ; }).statementName

            }
            setShared(model.PARAMETERS_SCREEN_M.OUTPUT, data);
            close();
        }
        cancel(){
            close();
        }
    }
    class StatementDto {
        statementCode : string ;
        statementName : string ;
        displayYearMonth : string;
        constructor() {
        }
        static fromApp(app) {
            let listStatementDto = [];
            let to = getText('QMM020_56');
            _.each(app, (item) => {
                let dto: StatementDto = new StatementDto();
                dto.statementCode = item.statementCode;
                dto.statementName = item.statementName;
                dto.displayYearMonth = this.convertMonthYearToString(item.startYearMonth)+ " " + to + " "+this.convertMonthYearToString(item.endYearMonth);;
                if(dto.statementName.length > 10){
                    setTimeout(function() {
                        $('.limited-label').css("border-left","none");
                        $('.limited-label').css("border-right","none");
                        $('#multi-list tr td:nth-child(2)').addClass("limited-label");
                    }, 50);
                }
                listStatementDto.push(dto);
            })
            return _.orderBy(listStatementDto,['statementCode']);
        }
        static convertMonthYearToString(yearMonth: any) {
            let self = this;
            let year: string, month: string;
            yearMonth = yearMonth.toString();
            year = yearMonth.slice(0, 4);
            month = yearMonth.slice(4, 6);
            return year + "/" + month;
        }

    }


}