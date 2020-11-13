/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />

module nts.uk.com.view.ccg015.e {

  @bean()
  export class ScreenModel extends ko.ViewModel {

    placementList: KnockoutObservableArray<Placement> = ko.observableArray([]);
    widgetList: KnockoutObservableArray<WidgetItem> = ko.observableArray([]);
    isNewMode: KnockoutObservable<boolean> = ko.observable(true);
    topPageCode: KnockoutObservable<string> = ko.observable('');
    layoutNo: KnockoutObservable<number> = ko.observable(0);
    params: any = {};
    layoutClone: JQuery;

    created(params: any) {
      const vm = this;
      vm.params = params;
    }

    mounted() {
      const vm = this;
      vm.draggableItem();
      vm.droppableItem();
      vm.draggableItemContainer2();
      vm.checkDataLayout(vm.params);
      // vm.removeItem();
    }

    checkDataLayout(params: any) {
      const vm = this;
      if (params) {
        if (params.topPageModel && params.topPageModel.topPageCode) {
          vm.topPageCode(params.topPageModel.topPageCode);
        }
        if (params.frame === 2) {
          vm.layoutNo(1);
        } else if (params.frame === 3) {
          vm.layoutNo(2);
        }
      }
      const layoutRquest = {
        topPageCode: vm.topPageCode(),
        layoutNo: vm.layoutNo()
      }
      vm.$blockui("show");
      vm.$ajax('/toppage/getLayout', layoutRquest).then((result: any) => {
        if (result) {
          vm.isNewMode(false)
          console.log(result);
        } else {
          vm.isNewMode(true);
        }
      }).always(() => {
        vm.$blockui("hide");
      });
    }

    saveListWidgetLayout() {
      const vm = this;
      vm.$blockui("show");
      let widgetTest: WidgetItem = new WidgetItemImpl();
      widgetTest.widgetType = 0;
      widgetTest.order = 1;
      vm.widgetList.push(widgetTest);

      let data: any = {
        cid: __viewContext.user.companyId,
        topPageCode: vm.topPageCode(),
        layoutNo: vm.layoutNo(),
        layoutType: 3,
        flowMenuCd: null,
        flowMenuUpCd: null,
        url: null,
        widgetSettings: [{ widgetType: 0, order:1 }, { widgetType: 3, order:2 }, { widgetType: 4, order:3 }],
      };

      vm.$ajax('/toppage/saveLayoutWidget', data).done(function () {
        vm.$dialog.info({ messageId: "Msg_15" })
      }).then((result: any) => {
        vm.isNewMode(false);
      }).always(() => {
        vm.$blockui("hide");
      });
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
              let widgetItem: WidgetItem;
              let order: number = 0;
              switch ($(this).attr("itemid")) {
                case "itm-1":
                  placementItem.placementId = "itm-1";
                  placementItem.url = "/nts.uk.at.web/view/ktg/005/a/index.xhtml";
                  placementItem.width = 0;
                  placementItem.height = 0;
                  placementItem.sort = 0;
                  vm.placementList.push(placementItem);
                  widgetItem.widgetType = 0;
                  widgetItem.order = 1;
                  vm.widgetList.push(widgetItem);
                  break;
                case "itm-2":
                  placementItem.placementId = "itm-2";
                  placementItem.url = "/nts.uk.at.web/view/ktg/001/a/index.xhtml";
                  placementItem.width = 0;
                  placementItem.height = 0;
                  placementItem.sort = 0;
                  vm.placementList.push(placementItem);
                  widgetItem.widgetType = 1;
                  widgetItem.order = 1;
                  vm.widgetList.push(widgetItem);
                  break;
                case "itm-3":
                  placementItem.placementId = "itm-3";
                  placementItem.url = "/nts.uk.at.web/view/ktg/004/a/index.xhtml";
                  placementItem.width = 0;
                  placementItem.height = 0;
                  placementItem.sort = 0;
                  vm.placementList.push(placementItem);
                  widgetItem.widgetType = 2;
                  widgetItem.order = 1;
                  vm.widgetList.push(widgetItem);
                  break;
                case "itm-4":
                  placementItem.placementId = "itm-4";
                  placementItem.url = "/nts.uk.at.web/view/ktg/026/a/index.xhtml";
                  placementItem.width = 0;
                  placementItem.height = 0;
                  placementItem.sort = 0;
                  vm.placementList.push(placementItem);
                  widgetItem.widgetType = 3;
                  widgetItem.order = 1;
                  vm.widgetList.push(widgetItem);
                  break;
                case "itm-5":
                  placementItem.placementId = "itm-5";
                  placementItem.url = "/nts.uk.at.web/view/ktg/027/a/index.xhtml";
                  placementItem.width = 0;
                  placementItem.height = 0;
                  placementItem.sort = 0;
                  vm.placementList.push(placementItem);
                  widgetItem.widgetType = 4;
                  widgetItem.order = 1;
                  vm.widgetList.push(widgetItem);
                  break;
                case "itm-6":
                  placementItem.placementId = "itm-6";
                  placementItem.url = "/nts.uk.at.web/view/kdp/001/a/index.xhtml";
                  placementItem.width = 0;
                  placementItem.height = 0;
                  placementItem.sort = 0;
                  vm.placementList.push(placementItem);
                  widgetItem.widgetType = 5;
                  widgetItem.order = 1;
                  vm.widgetList.push(widgetItem);
                  break;
                case "itm-7":
                  placementItem.placementId = "itm-7";
                  placementItem.url = "/nts.uk.at.web/view/ktg/031/a/index.xhtml";
                  placementItem.width = 0;
                  placementItem.height = 0;
                  placementItem.sort = 0;
                  vm.placementList.push(placementItem);
                  widgetItem.widgetType = 6;
                  widgetItem.order = 1;
                  vm.widgetList.push(widgetItem);
                  break;
                case "itm-8":
                  placementItem.placementId = "itm-8";
                  placementItem.url = "/nts.uk.com.web/view/ccg/005/a/index.xhtml";
                  placementItem.width = 0;
                  placementItem.height = 0;
                  placementItem.sort = 0;
                  vm.placementList.push(placementItem);
                  widgetItem.widgetType = 7;
                  widgetItem.order = 1;
                  vm.widgetList.push(widgetItem);
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

    // ウィジェットを取消する
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
      const vm = this;
      vm.$window.close();
    }
  }

  interface WidgetItem {
    widgetType: number;
    order: number;
  }

  class WidgetItemImpl implements WidgetItem {
    widgetType: number;
    order: number;
  }

  export class Placement {
    placementId: string;
    url: string;
    width: number;
    height: number;
    sort: number;
  }
}