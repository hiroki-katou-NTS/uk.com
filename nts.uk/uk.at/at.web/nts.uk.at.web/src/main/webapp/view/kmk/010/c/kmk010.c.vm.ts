module nts.uk.at.view.kmk010.c {

    import OvertimeBRDItemDto = nts.uk.at.view.kmk010.a.service.model.OvertimeBRDItemDto;
    import EnumConstantDto = nts.uk.at.view.kmk010.a.service.model.EnumConstantDto;
    import OvertimeBRDItemModel = nts.uk.at.view.kmk010.a.viewmodel.OvertimeBRDItemModel;
    
    export module viewmodel {

        export class ScreenModel {
            lstOvertimeBRDItemModel: OvertimeBRDItemModel[];
            lstProductNumber: EnumConstantDto[];

           constructor() {
               var self = this;
               self.lstOvertimeBRDItemModel = [];
               self.lstProductNumber = [];
           }
            /**
             * start page when init data
             */
           public startPage(): JQueryPromise<any> {
               var self = this;
               var dfd = $.Deferred();
               service.findAllProductNumber().done(function(data){
                  self.lstProductNumber = data; 
               });
               service.findAllOvertimeBRDItem().done(function(data) {
                   self.lstOvertimeBRDItemModel = [];
                   for (var dto of data) {
                       var model: OvertimeBRDItemModel = new OvertimeBRDItemModel();
                       model.updateData(dto);
                       self.lstOvertimeBRDItemModel.push(model);
                   }
                   dfd.resolve(self);
               });
               return dfd.promise();
           }
            /**
             * save overtime break down item on lick button save
             */
            private saveOvertimeBRDItem(): void{
                var self = this;
                
                // convert model to dto
                var overtimes: OvertimeBRDItemDto[] = [];
                for (var model of self.lstOvertimeBRDItemModel) {
                    overtimes.push(model.toDto());
                }
                
                // call service save all overtime
                service.saveAllOvertimeBRDItem(overtimes).done(function(){
                   console.log('YES'); 
                }).fail(function(error){
                    
                });    
            }
        }

    }
}