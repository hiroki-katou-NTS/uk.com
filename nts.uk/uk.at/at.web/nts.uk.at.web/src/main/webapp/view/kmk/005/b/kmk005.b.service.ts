module nts.uk.at.view.kmk005.b {
    export module service {
        var paths: any = {
            getListBonusPayTimeItem: "at/share/bonusPayTimeItem/getListBonusPayTimeItem",
            getListSpecialBonusPayTimeItem: "at/share/bonusPayTimeItem/getListSpecialBonusPayTimeItem",
            addListBonusPayTimeItem:"at/share/bonusPayTimeItem/addListBonusPayTimeItem",
            updateListBonusPayTimeItem:"at/share/bonusPayTimeItem/updateListBonusPayTimeItem",
            checkUseArt:"at/share/bonusPayTimeItem/checkUseArt"
            
        }
          export function checkUseArt(lstUseArt,lstUseSpecArt) {
              var arts = {lstUseArt:lstUseArt,lstUseSpecArt:lstUseSpecArt}
             return nts.uk.request.ajax(paths.checkUseArt,arts);
        }
        
        export function getListSpecialBonusPayTimeItem(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListSpecialBonusPayTimeItem);
        }
        
        export function addListBonusPayTimeItem(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.addListBonusPayTimeItem,command);
        }
        
        export function updateListBonusPayTimeItem(command): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.updateListBonusPayTimeItem,command);
        }
        
         export function getListBonusPTimeItem(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListBonusPayTimeItem);
        }
        
    }
}