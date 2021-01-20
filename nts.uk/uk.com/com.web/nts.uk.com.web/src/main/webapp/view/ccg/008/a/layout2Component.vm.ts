/// <reference path='../../../../lib/nittsu/viewcontext.d.ts' />
module nts.uk.com.view.ccg008.a.Layout2ComponentViewModel {
  @component({
    name: "layout2-component",
    template: `
      <span data-bind="if: $component.isLayout == 2">
        <div class="widget_contents" data-bind="foreach: $component.lstWidgetLayout">
          <span data-bind="if: url.indexOf('.xhtml') > -1">
            <iframe style="width:450px" data-bind="attr: { src: url }" />
          </span>
          <span data-bind="if: url.indexOf('.js') > -1">
            <div data-bind="attr:{ id: 'WG2-' + $index() }">
              <div data-bind="component: { name: name }" style="margin-bottom: 10px; height:100%;" ></div>
            </div>
          </span>
        </div>
      </span>

      <span data-bind="if: $component.isLayout == 3">
        <div class="widget_contents" data-bind="foreach: $component.lstWidgetLayout">
          <span data-bind="if: url.indexOf('.xhtml') > -1">
            <iframe style="width:450px" data-bind="attr: { src: url }" />
          </span>
          <span data-bind="if: url.indexOf('.js') > -1">
            <div data-bind="attr:{ id: 'WG3-' + $index() }">
              <div data-bind="component: { name: name  }" style="margin-bottom: 10px; height:100%;" ></div>
            </div>
          </span>
        </div>
      </span>
    `,
  })
  export class Layout2ComponentViewModel extends ko.ViewModel {
    lstWidgetLayout: KnockoutObservableArray<ItemLayout> = ko.observableArray([]);
    isLayout: number;

    created(param: any) {
      const vm = this;
      const layout2 = param.item();
      const origin: string = window.location.origin;
      vm.isLayout = param.isLayout;
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
          url = "/view/ktg/005/a/ktg005.a.component.js";
          name = "ktg-005-a";
          break;
        case 1:
          url = "/view/ktg/001/a/ktg001.a.component.js";
          name = "kgt-001-a";
          break;
        case 2:
          url = "/view/ktg/004/a/ktg004.a.component.js";
          name = "ktg-004-a";
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
        case 7:
          url = "/view/ccg/005/a/index.xhtml";
          name = '';
          break;
      }
      return { url: url, name: name };
    }

    mounted() {
      const vm = this;
      if ($('#WG2-0')) {
        $('#WG2-0').resizable({
          grid: [10000, 1]
        });
        $('#WG2-0').resize(() => {
          const wg0 = document.getElementById('WG2-0');
          const wg0Child = wg0.firstElementChild.firstElementChild as any;
          wg0Child.style.height = '100%';
        })
      }
      if ($('#WG2-1')) {
        $('#WG2-1').resizable({
          grid: [10000, 1]
        });
        $('#WG2-1').resize(() => {
          const wg0 = document.getElementById('WG2-1');
          const wg0Child = wg0.firstElementChild.firstElementChild as any;
          wg0Child.style.height = '100%';
        })
      }
      if ($('#WG2-2')) {
        $('#WG2-2').resizable({
          grid: [10000, 1]
        });
        $('#WG2-2').resize(() => {
          const wg0 = document.getElementById('WG2-2');
          const wg0Child = wg0.firstElementChild.firstElementChild as any;
          wg0Child.style.height = '100%';
        })
      }

      if ($('#WG2-3')) {
        $('#WG2-3').resizable({
          grid: [10000, 1]
        });
        $('#WG2-3').resize(() => {
          const wg0 = document.getElementById('WG2-3');
          const wg0Child = wg0.firstElementChild.firstElementChild as any;
          wg0Child.style.height = '100%';
        })
      }

      if ($('#WG2-4')) {
        $('#WG2-4').resizable({
          grid: [10000, 1]
        });
        $('#WG2-4').resize(() => {
          const wg0 = document.getElementById('WG2-4');
          const wg0Child = wg0.firstElementChild.firstElementChild as any;
          wg0Child.style.height = '100%';
        })
      }

      if ($('#WG3-0')) {
        $('#WG3-0').resizable({
          grid: [10000, 1]
        });
        $('#WG3-0').resize(() => {
          const wg0 = document.getElementById('WG3-0');
          const wg0Child = wg0.firstElementChild.firstElementChild as any;
          wg0Child.style.height = '100%';
        })
      }
      if ($('#WG3-1')) {
        $('#WG3-1').resizable({
          grid: [10000, 1]
        });
        $('#WG3-1').resize(() => {
          const wg0 = document.getElementById('WG3-1');
          const wg0Child = wg0.firstElementChild.firstElementChild as any;
          wg0Child.style.height = '100%';
        })
      }
      if ($('#WG3-2')) {
        $('#WG3-2').resizable({
          grid: [10000, 1]
        });
        $('#WG3-2').resize(() => {
          const wg0 = document.getElementById('WG3-2');
          const wg0Child = wg0.firstElementChild.firstElementChild as any;
          wg0Child.style.height = '100%';
        })
      }

      if ($('#WG3-3')) {
        $('#WG3-3').resizable({
          grid: [10000, 1]
        });
        $('#WG3-3').resize(() => {
          const wg0 = document.getElementById('WG3-3');
          const wg0Child = wg0.firstElementChild.firstElementChild as any;
          wg0Child.style.height = '100%';
        })
      }

      if ($('#WG3-4')) {
        $('#WG3-4').resizable({
          grid: [10000, 1]
        });
        $('#WG3-4').resize(() => {
          const wg0 = document.getElementById('WG3-4');
          const wg0Child = wg0.firstElementChild.firstElementChild as any;
          wg0Child.style.height = '100%';
        })
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
