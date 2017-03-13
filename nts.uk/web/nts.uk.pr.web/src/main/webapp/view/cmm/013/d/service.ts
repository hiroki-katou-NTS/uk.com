module cmm013.d.service {
    var paths:any = {
        getPositionInfor : "basic/position/findallposition/",
        pathDeletePosition: "basic/position/deletedata",
        pathUpdatePosition: "basic/position/updatedata"
    }
    
    /**
     * Get list layout master new history
     */
    export function getPosition(historyId: string): JQueryPromise<model.JobHistDto> {
        var dfd = $.Deferred<any>();
        nts.uk.request.ajax("com",paths.findAllPosition + historyId)
       
            .done(function(res: any){
                dfd.resolve(res);
            })
            .fail(function(res) {
                dfd.reject(res);
            })
        return dfd.promise(); 
    }
    
    export function deletePosition(position : model.JobHistDto){
        var dfd = $.Deferred<Array<any>>();  
        var _path = nts.uk.text.format(paths.pathDeleteLayout, position);
        nts.uk.request.ajax(paths.pathDeletePosition, position)
        .done(function(res: Array<any>){
            dfd.resolve(res);    
        }).fail(function(res){
            dfd.reject(res);
        })
        
        return dfd.promise(); 
    }
    
    export function updatePosition(position : model.JobHistDto){
        var dfd = $.Deferred<Array<any>>();  
        nts.uk.request.ajax(paths.pathUpdatePosition, position).done(function(res: Array<any>){
            dfd.resolve(res);    
        }).fail(function(res){
            dfd.reject(res);
        })
        
        return dfd.promise(); 
    }
    
   /**
           * Model namespace.
        */
        export module model {
            // layout
            export class JobHistDto {
                companyCode: string;              
                startDate: number;              
                endDate: number;        
                histortyId: string;
            }

        }
}