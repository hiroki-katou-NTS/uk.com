module nts.uk.at.view.kml004.d.viewmodel {
    import setSharedD = nts.uk.ui.windows.setShared;
    import getSharedD = nts.uk.ui.windows.getShared;
    
    export class ScreenModel {
        // list item time no received from  
        itemsSwap: KnockoutObservableArray<TotalSet>;
        // list columns 
        columns: KnockoutObservableArray<any>;
        currentCodeListSwap: KnockoutObservableArray<TotalSet>;
        // object received from screen A
        object: KnockoutObservable<any>;
        // COPY list received
        lst: KnockoutObservableArray<any>;
        constructor() {
            let self = this;
            self.itemsSwap = ko.observableArray([]);
            self.columns = ko.observableArray([
                { headerText: nts.uk.resource.getText("KML004_40"), key: 'totalTimeNo', width: 70 },
                { headerText: nts.uk.resource.getText("KML004_41"), key: 'totalTimeName', width: 250, formatter: _.escape }
            ]);
            self.object = ko.observable(getSharedD("KML004A_CNT_SET"));
            self.currentCodeListSwap = ko.observableArray([]);
            self.currentCodeListSwap(self.object().cntSetls);
            self.lst = ko.observableArray([]);
            self.currentCodeListSwap(self.object().cntSetls);
            self.lst(self.object().cntSetls);
        }  

        /** get total time */
        getTotalTime(): JQueryPromise<any> {
            let self = this;
            let dfd = $.Deferred();
            service.getAll().done((lstSet) => {
                if(lstSet.length > 0){
                     _.forEach(lstSet, function(item) {
                        var param = new TotalSet(self.object().categoryCode, self.object().totalItemNo, item.totalCountNo, item.totalTimesName);
                        self.itemsSwap().push(param);
                    }); 
                }
                dfd.resolve();
            });
            return dfd.promise();
        }
        
        /**
         * Event on start page.
         */
        public startPage(): JQueryPromise<any> {
            var self = this;
            var dfd = $.Deferred();
            // get left list
            self.getTotalTime().done(function(data1) {
                self.currentCodeListSwap(self.lst());
                var totalItemNoList = _.map(self.lst(), function(item) { return item.totalTimeNo; });
                var itemSelected = _.filter(self.itemsSwap(), function(item) {
                    return _.indexOf(totalItemNoList, item.totalTimeNo) >= 0;    
                });
                self.currentCodeListSwap(itemSelected);
                dfd.resolve();
            });
            return dfd.promise();
        }

        /**
         * click register button, send cal day set to screen A 
         */
        public save(): void {
            var self = this;
            if(self.currentCodeListSwap().length > 10){
                nts.uk.ui.dialog.info({ messageId: "Msg_459" });
            }else{
                setSharedD('KML004D_CNT_SET', self.currentCodeListSwap());
                nts.uk.ui.windows.close(); 
            }     
        }

        /**   
         * Event on click cancel button.
         */
        public cancel(): void {
            nts.uk.ui.windows.close();  
        }
    }

    export class TotalSet {
        categoryCode: string;
        totalItemNo: number;
        totalTimeNo: number;
        totalTimeName: string;
        constructor(categoryCode: string, totalItemNo: number, totalTimeNo: number, totalTimeName: string) {
            this.categoryCode = categoryCode;
            this.totalItemNo = totalItemNo;
            this.totalTimeNo = totalTimeNo;
            this.totalTimeName = totalTimeName;
        }
    }
}