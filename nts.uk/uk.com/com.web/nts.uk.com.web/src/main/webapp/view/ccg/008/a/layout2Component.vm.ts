/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg008.a.Layout2ComponentViewModel {
  @component({
    name: "layout2-component",
    template: `
      <div class="widget_contents" data-bind="foreach: $component.lstWidgetLayout">
        <span data-bind="if: url.indexOf('.xhtml') > -1">
          <iframe style="width:450px" data-bind="attr: { src: url }" />
        </span>
        <span data-bind="if: url.indexOf('.js') > -1">
          <div data-bind="attr:{ id: 'WG' + $index() }">
            <div data-bind="component: { name: name }" style="margin-bottom: 10px; height:100%;" ></div>
          </div>
        </span>
      </div>
    `,
  })
  export class Layout2ComponentViewModel extends ko.ViewModel {
    lstWidgetLayout: KnockoutObservableArray<ItemLayout> = ko.observableArray(
      []
    );

    created(param: any) {
      const vm = this;
      const layout2 = param.item();
      const origin: string = window.location.origin;
      let dataLayout: ItemLayout[] = [];
      vm.lstWidgetLayout([]);
      if (layout2) {
        _.each(layout2, (item: WidgetSettingDto) => {
          const itemLayout: ItemLayout = new ItemLayout();
          itemLayout.url = origin + vm.getUrlAndName(item.widgetType).url;
          itemLayout.name = vm.getUrlAndName(item.widgetType).name;
          itemLayout.order = item.order;
          dataLayout.push(itemLayout);
        });
        dataLayout = _.orderBy(dataLayout, ["order"], ["asc"]);
        vm.lstWidgetLayout(dataLayout);
      }
      
    }

    getUrlAndName(type: any) {
      let url = "";
      let name = "";
      switch (type) {
        case 0:
          url = "/nts.uk.at.web/view/ktg/005/a/index.xhtml";
          name = "";
          break;
        case 1:
          url = "/nts.uk.at.web/view/ktg/001/a/index.xhtml";
          name = "";
          break;
        case 2:
          url = "/nts.uk.at.web/view/ktg/004/a/index.xhtml";
          name = "";
          break;
        case 3:
          url = "/view/ktg/026/a/ktg026component.a.vm.js";
          name = "ktg026-component";
          break;
        case 4:
          url = "/view/ktg/027/a/ktg027.a.vm.js";
          name = "ktg027-component";
          break;
        case 5:
          url = "/nts.uk.at.web/view/kdp/001/a/index.xhtml";
          name = "";
          break;
        case 6:
          url = "/view/ktg/031/a/ktg031.a.vm.js";
          name = "ktg031-component";
          break;
        // case 7:
        //   url = "/view/ccg/005/a/index.xhtml";
        //   name = '';
        //   break;
      }
      return { url: url, name: name };
    }

    mounted() {
      const vm = this;
      if ($('#WG0')) {
        $('#WG0').resizable({
          grid: [10000, 1]
        });
      }
      if ($('#WG1')) {
        $('#WG1').resizable({
          grid: [10000, 1]
        });
      }
      if($('#WG2')) {
        $('#WG2').resizable({
          grid: [10000, 1]
        });
      }

      if($('#WG3')) {
        $('#WG3').resizable({
          grid: [10000, 1]
        });
      }

      if($('#WG4')) {
        $('#WG4').resizable({
          grid: [10000, 1]
        });
      }
      
      
      vm.$el.querySelectorAll("iframe").forEach((frame) => {
        const targetNode = frame.contentDocument;

        if (targetNode) {
          const config = { childList: true, subtree: true };

          const callback = function () {
            const width = frame.contentWindow.innerWidth;
            const height = frame.contentWindow.innerHeight;
            frame.style.width = width.toString();
            frame.style.height = height.toString();
          };
          const observer = new MutationObserver(callback);
          observer.observe(targetNode, config);
        }
      });

      
    }
  }

  export class ItemLayout {
    url: string;
    html: string;
    name: string;
    order: number;
    constructor(init?: Partial<ItemLayout>) {
      $.extend(this, init);
    }
  }

  export class WidgetSettingDto {
    widgetType: number;
    order: number;
    constructor(init?: Partial<WidgetSettingDto>) {
      $.extend(this, init);
    }
  }
}
