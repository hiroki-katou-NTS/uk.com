module nts.uk.at.view.kmk010.b {

    import OvertimeDto = nts.uk.at.view.kmk010.a.service.model.OvertimeDto;
    import OvertimeModel = nts.uk.at.view.kmk010.a.viewmodel.OvertimeModel;
    
    export module viewmodel {

        export class ScreenModel {
            lstOvertimeModel: OvertimeModel[];


           constructor() {
               var self = this;
               self.lstOvertimeModel = [];
           }
            /**
             * start page when init data
             */
           public startPage(): JQueryPromise<any> {
               var self = this;
               var dfd = $.Deferred();
               service.findAllOvertime().done(function(data) {
                   self.lstOvertimeModel = [];
                   for (var dto of data) {
                       var model: OvertimeModel = new OvertimeModel();
                       model.updateData(dto);
                       self.lstOvertimeModel.push(model);
                   }
                   dfd.resolve(self);
               });
               return dfd.promise();
           }
            /**
             * save overtime on lick button save
             */
            private saveOvertime(): void{
                var self = this;
                
                // convert model to dto
                var overtimes: OvertimeDto[] = [];
                for (var model of self.lstOvertimeModel) {
                    overtimes.push(model.toDto());
                }
                console.log(overtimes);
                
                // call service save all overtime
                service.saveAllOvertime(overtimes).done(function(){
                   console.log('YES'); 
                }).fail(function(error){
                    
                });    
            }
        }

    }
}