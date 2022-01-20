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
          	 var art = {lstUseArt:lstUseArt,lstUseSpecArt:lstUseSpecArt}
             return nts.uk.request.ajax(paths.checkUseArt,art);
        }

        export function getListSpecialBonusPayTimeItem(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListSpecialBonusPayTimeItem);
        }

        export function addListBonusPayTimeItem(bonusPayTimeItemListCommand,bonusPayTimeItemSpecListCommand): JQueryPromise<any> {
        	var commands = {bonusPayTimeItemListCommand:bonusPayTimeItemListCommand,bonusPayTimeItemSpecListCommand:bonusPayTimeItemSpecListCommand};
            return nts.uk.request.ajax(paths.addListBonusPayTimeItem,commands);
        }

        export function updateListBonusPayTimeItem(bonusPayTimeItemListCommand,bonusPayTimeItemSpecListCommand): JQueryPromise<any> {
        	var commands = {bonusPayTimeItemListCommand:bonusPayTimeItemListCommand,bonusPayTimeItemSpecListCommand:bonusPayTimeItemSpecListCommand};
            return nts.uk.request.ajax(paths.updateListBonusPayTimeItem,commands);
        }

         export function getListBonusPTimeItem(): JQueryPromise<any> {
            return nts.uk.request.ajax(paths.getListBonusPayTimeItem);
        }

    }
}