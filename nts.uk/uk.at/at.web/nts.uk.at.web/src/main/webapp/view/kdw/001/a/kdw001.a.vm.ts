module nts.uk.at.view.kdw001.a {
    import getText = nts.uk.resource.getText;
    export module viewmodel {
        export class ScreenModel {

            //shime list.
            closureList: Array<Closure>;

            closureID: any;

            constructor() {

            }

            start() {
                let self = this;
                service.findAllClosure().done(function(data) {
                    let items = _.map(data, item => {
                        return new Closure(item);
                    });
                    self.closureList = items;
                });

                /*  service.getMaxClosure().done((data)=>{
                      if(!nts.uk.util.isNullOrUndefined(data)){
                          self.closureID =  data.closureID;
                      }else{
                          self.closureID = '1';
                          nts.uk.ui.dialog.alert("closureID doesn't exist!");
                      }
                  });
                  
                  */
                // closureID get form CCG001. 
                self.closureID = 1;

            }

            opendScreenF() {
                nts.uk.request.jump("/view/kdw/001/f/index.xhtml");
            }

            opendScreenJC() {
                let self = this;
                //nts.uk.request.jump("/view/kdw/001/j/index.xhtml", { "activeStep": 0, "screenName": "J" });
                //  nts.uk.request.jump("/view/kdw/001/j/index.xhtml", {"closureID":self.closureID});
                nts.uk.request.jump("/view/kdw/001/j/index.xhtml");
            }

            opendScreenBC() {
                let self = this;
                //nts.uk.request.jump("/view/kdw/001/b/index.xhtml", { "activeStep": 0, "screenName": "B" });
                //nts.uk.request.jump("/view/kdw/001/b/index.xhtml", {"closureID":self.closureID});
                nts.uk.request.jump("/view/kdw/001/b/index.xhtml");
            }
        }

        /**
         * The Class ClosureFindDto.
         */
        export interface IClosure {
            closureId: number;
            useClassification: number;
            month: number;
            closureSelected: ClosureHistoryMaster;
            closureHistories: Array<ClosureHistoryMaster>;
        }

        export class Closure {
            closureId: number;
            useClassification: number;
            month: number;
            closureSelected: ClosureHistoryMaster;
            closureHistories: Array<ClosureHistoryMaster>;

            constructor(x: IClosure) {
                let self = this;
                if (x) {
                    self.closureId = x.closureId;
                    self.useClassification = x.useClassification;
                    self.month = x.month;
                    self.closureSelected = x.closureSelected;
                    self.closureHistories = x.closureHistories;
                } else {
                    self.closureId = 0;
                    self.useClassification = 0;
                    self.month = 0;
                    self.closureSelected = null;
                    self.closureHistories = null;
                }
            }
        }

        /**
         * The Class ClosureHistoryMasterDto.
         */
        export class ClosureHistoryMaster {
            historyId: string;
            closureId: number;
            endDate: number;
            startDate: number;
        }


    }
}
