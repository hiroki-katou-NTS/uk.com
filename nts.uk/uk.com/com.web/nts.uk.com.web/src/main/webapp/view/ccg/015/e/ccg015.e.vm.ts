/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg015.e.screenModel {
  import setShared = nts.uk.ui.windows.setShared;
  import getShared = nts.uk.ui.windows.getShared;

  @bean()
  export class ViewModel extends ko.ViewModel {

    placementList: KnockoutObservableArray<Placement> = ko.observableArray([]);
    layoutClone: JQuery;

    created() {

    }

    mounted() {
      const vm = this;
      vm.draggableItem();
      vm.droppableItem();
      vm.draggableItemContainer2();
      // vm.removeItem();
    }

    draggableItem() {
      $('.box-item').draggable({
        revert: true,
        revertDuration: 0,
        stack: ".draggable",
        cursor: "pointer",
        containment: '#content_layout',
        iframeFix: true
      });
    }

    
    draggableItemContainer2() {
      // ko.bindingHandlers.sortableList = {
      //   init: function(element, valueAccessor) {
      //     var list = valueAccessor();
      //     $(element).sortable({
      //     })
      //   }
      // }
    }

    droppableItem() {
      const vm = this;
      $("#container2").droppable({
        drop: function(event: any, ui) {
          let itemid = $(event.originalEvent.toElement).attr("itemid");
          $('.box-item').each(function() {
            if ($(this).attr("itemid") === itemid) {
              let placementItem: Placement = new Placement();
              switch ($(this).attr("itemid")) {
                case "itm-1":
                  placementItem.placementId = "itm-1";
                  placementItem.url = "/nts.uk.at.web/view/ktg/005/a/index.xhtml";
                  placementItem.width = 0;
                  placementItem.height = 0;
                  placementItem.sort = 0;
                  vm.placementList.push(placementItem);
                  break;
                case "itm-2":
                  placementItem.placementId = "itm-2";
                  placementItem.url = "/nts.uk.at.web/view/ktg/001/a/index.xhtml";
                  placementItem.width = 0;
                  placementItem.height = 0;
                  placementItem.sort = 0;
                  vm.placementList.push(placementItem);
                  break;
                case "itm-3":
                  placementItem.placementId = "itm-3";
                  placementItem.url = "/nts.uk.at.web/view/ktg/004/a/index.xhtml";
                  placementItem.width = 0;
                  placementItem.height = 0;
                  placementItem.sort = 0;
                  vm.placementList.push(placementItem);
                  break;
                case "itm-4":
                  placementItem.placementId = "itm-4";
                  placementItem.url = "/nts.uk.at.web/view/ktg/026/a/index.xhtml";
                  placementItem.width = 0;
                  placementItem.height = 0;
                  placementItem.sort = 0;
                  vm.placementList.push(placementItem);
                  break;
                case "itm-5":
                  placementItem.placementId = "itm-5";
                  placementItem.url = "/nts.uk.at.web/view/ktg/027/a/index.xhtml";
                  placementItem.width = 0;
                  placementItem.height = 0;
                  placementItem.sort = 0;
                  vm.placementList.push(placementItem);
                  break;
                case "itm-6":
                  placementItem.placementId = "itm-6";
                  placementItem.url = "/nts.uk.at.web/view/kdp/001/a/index.xhtml";
                  placementItem.width = 0;
                  placementItem.height = 0;
                  placementItem.sort = 0;
                  vm.placementList.push(placementItem);
                  break;
                case "itm-7":
                  placementItem.placementId = "itm-7";
                  placementItem.url = "/nts.uk.at.web/view/ktg/031/a/index.xhtml";
                  placementItem.width = 0;
                  placementItem.height = 0;
                  placementItem.sort = 0;
                  vm.placementList.push(placementItem);
                  break;
                case "itm-8":
                  placementItem.placementId = "itm-8";
                  placementItem.url = "/nts.uk.com.web/view/ccg/005/a/index.xhtml";
                  placementItem.width = 0;
                  placementItem.height = 0;
                  placementItem.sort = 0;
                  vm.placementList.push(placementItem);
                  break;
              }
              // $(this).addClass("item_opacity").clone().removeClass("box-item item_opacity").addClass("item_choose fix_style_item").appendTo("#container2");
              $(this).addClass("item_opacity").draggable({
                disabled: true
              });
            }
          });
        },
      });
    }

    // Click button x item content 2
    removeItem(placementId: string) {
      // Remove item in content 2
      $("#container2").find("[itemid='" + placementId + "']").remove();
      // Find item drag in content 1
      let itemDrag: JQuery = $("#container1").find("[itemid='" + placementId + "']");
      // Enable item drag in content 1
      $(itemDrag).removeClass("item_opacity").draggable({
        disabled: false
      });
    }
   
    close() {
      nts.uk.ui.windows.close();
    }
  }
    

  export class Placement {
    placementId: string;
    url: string;
    width: number;
    height: number;
    sort: number;
  }
}