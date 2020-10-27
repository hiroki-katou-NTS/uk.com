/// <reference path="../../../../lib/nittsu/viewcontext.d.ts" />

module nts.uk.com.view.ccg034.d {

  // URL API backend
  const API = {
    // ...
  }

  const KEY_DATA_ITEM_CLIENT_ID: string = 'data-item-client-id';
  const KEY_DATA_PART_TYPE: string = 'data-part-type';
  const MENU_CREATION_LAYOUT_ID: string = 'menu-creation-layout';
  const ITEM_HIGHLIGHT_ID: string = 'item-highlight';
  const ITEM_COPY_PLACEHOLDER_ID: string = 'item-copy-placeholder';
  const CELL_SIZE: number = 40;
  const CREATION_LAYOUT_WIDTH: number = 1920;
  const CREATION_LAYOUT_HEIGHT: number = 1080;

  @bean()
  export class ScreenModel extends ko.ViewModel {
    $menuCreationLayoutContainer: JQuery = null;
    $menuCreationLayout: JQuery = null;
    $hoverHighlight: JQuery = null;
    $copyPlaceholder: JQuery = null;
    $listPart: JQuery[] = [];
    partClientId: number = 0;
    mapPartData: any = {};
    layoutSizeText: KnockoutObservable<string> = ko.observable('');

    isMouseInsideLayout: KnockoutObservable<boolean> = ko.observable(false);
    isCopying: KnockoutObservable<boolean> = ko.observable(false);
    copyingPartId: KnockoutObservable<number> = ko.observable(null);
    layoutOffsetLeft: KnockoutObservable<number> = ko.observable(null);
    layoutOffsetTop: KnockoutObservable<number> = ko.observable(null);

    mounted() {
      const vm = this;
      // Init text resource
      vm.layoutSizeText(vm.$i18n('CCG034_50', [CREATION_LAYOUT_WIDTH.toString(), CREATION_LAYOUT_HEIGHT.toString()]));
      // Store creation layout as class variable for easier access
      vm.$menuCreationLayout = $(`#${MENU_CREATION_LAYOUT_ID}`);
      vm.$menuCreationLayoutContainer = $('.menu-creation-layout-container');
      vm.$menuCreationLayout
        .outerWidth(CREATION_LAYOUT_WIDTH)
        .outerHeight(CREATION_LAYOUT_HEIGHT);
      // Init dragable item
      $(".menu-creation-option").draggable({
        appendTo: `#${MENU_CREATION_LAYOUT_ID}`,
        helper: "clone",
        start: (event, ui) => {
          vm.startDragItemFromMenu(ui);
        },
        drag: (event, ui) => {
          const partSize = vm.getPartSize(ui.helper.attr(KEY_DATA_PART_TYPE));
          vm.renderHoveringItemOnDrag(ui, partSize.width, partSize.height);
        },
        stop: (event, ui) => {
          vm.$hoverHighlight.remove();
          if (vm.isMouseInsideLayout()) {
            vm.createItemFromMenu(ui, ui.helper.attr(KEY_DATA_PART_TYPE));
          }
        },
      });
      // Init dropable layout
      vm.$menuCreationLayout
        .droppable({
          accept: ".menu-creation-item-container",
        })
        .mouseenter(() => vm.isMouseInsideLayout(true))
        .mouseleave(() => vm.isMouseInsideLayout(false))
        .mousedown((event) => {
          // If layout in copy mode and mouse cursor is stay inside layout
          if (vm.isCopying() && vm.isMouseInsideLayout()) {
            // Stop copy mode
            vm.isCopying(false);
            // Clear mouse event handler
            vm.$menuCreationLayout.off('mousemove');
            // Remove placeholder
            vm.$copyPlaceholder.remove();
            vm.$hoverHighlight.remove();
            // Create copy item
            const offsetX = event.pageX - vm.layoutOffsetLeft() + vm.$menuCreationLayoutContainer.scrollLeft();
            const offsetY = event.pageY - vm.layoutOffsetTop() + vm.$menuCreationLayoutContainer.scrollTop();
            // Calculate copy item div position
            const oldPartData = vm.mapPartData[vm.copyingPartId()];
            const positionTop: number = vm.calculatePositionTop(oldPartData.height, offsetY);
            const positionLeft: number = vm.calculatePositionLeft(oldPartData.width, offsetX);
            // Check overlap
            const overlappingParts: JQuery[] = vm.getOverlappingPart(new PartData({
              width: oldPartData.width,
              height: oldPartData.height,
              positionTop: positionTop,
              positionLeft: positionLeft,
            }));
            if (!overlappingParts.length) {
              // Create new part div
              const newPartData: PartData = vm.copyPartData(oldPartData, positionTop, positionLeft);
              vm.createDOMFromData(newPartData);
            }
          }
        });
    }

    /**
     * Start drag item from menu
     * @param item
     * @param width
     * @param height
     */
    private startDragItemFromMenu(item: JQueryUI.DraggableEventUIParams) {
      // Init size + style for dragging item
      item.helper.css({ 'opacity': '0.7' });
    }

    /**
     * Create new item on drop from menu
     * @param item
     */
    private createItemFromMenu(part: JQueryUI.DraggableEventUIParams, partType: string) {
      const vm = this;
      const partSize = vm.getPartSize(partType);
      // Calculate new part div position
      const positionTop: number = part.position.top > 0 ? Math.round(part.position.top / CELL_SIZE) * CELL_SIZE : 0;
      const positionLeft: number = part.position.left > 0 ? Math.round(part.position.left / CELL_SIZE) * CELL_SIZE : 0;
      // Check overlap
      const overlappingParts: JQuery[] = vm.getOverlappingPart(new PartData({
        width: partSize.width,
        height: partSize.height,
        positionTop: positionTop,
        positionLeft: positionLeft,
      }));
      if (!overlappingParts.length) {
        // Create new part div
        const newPartData: PartData = vm.createDefaultPartData(partType, partSize, positionTop, positionLeft);
        // Check if overlap is allowed or not
        const $newPart: JQuery = vm.createDOMFromData(newPartData);
        // Open PartSetting Dialog
        vm.openPartSettingDialog($newPart, true);
      }
    }

    /**
     * Create new DOM based on part data
     */
    private createDOMFromData(partData: PartData): JQuery {
      const vm = this;
      let $newPartTemplate = null;
      switch (partData.partType) {
        case MenuPartType.PART_MENU:
          $newPartTemplate = $("<div>", { "class": 'menu-creation-item-container' }).append($('<div>', { 'class': 'menu-creation-item part-menu' }));
          break;
        case MenuPartType.PART_LABEL:
          $newPartTemplate = $("<div>", { "class": 'menu-creation-item-container' }).append($('<div>', { 'class': 'menu-creation-item part-label' }));
          break;
        case MenuPartType.PART_LINK:
          $newPartTemplate = $("<div>", { "class": 'menu-creation-item-container' }).append($('<div>', { 'class': 'menu-creation-item part-link' }));
          break;
        case MenuPartType.PART_ATTACHMENT:
          $newPartTemplate = $("<div>", { "class": 'menu-creation-item-container' }).append($('<div>', { 'class': 'menu-creation-item part-attachment' }));
          break;
        case MenuPartType.PART_IMAGE:
          $newPartTemplate = $("<div>", { "class": 'menu-creation-item-container' }).append($('<div>', { 'class': 'menu-creation-item part-image' }));
          break;
        case MenuPartType.PART_ARROW:
          $newPartTemplate = $("<div>", { "class": 'menu-creation-item-container' }).append($('<div>', { 'class': 'menu-creation-item part-arrow' }));
          break;
        default:
          $newPartTemplate = $("<div>", { "class": 'menu-creation-item-container' }).append($('<div>', { 'class': 'menu-creation-item part-menu' }));
          break;
      }
      const $newPart: JQuery = vm.renderPartDOM(
        $newPartTemplate,
        partData.partType,
        partData);
      // Render div setting
      const $partSetting: JQuery = $("<div>", { "class": 'part-setting' })
        .hover(
          (handlerIn) => vm.onPartClickSetting($newPart, true),
          (handlerOut) => vm.onPartClickSetting($newPart, false));
      const $partSettingPopup: JQuery = $("<div>", { "class": 'part-setting-popup' })
        .css({ 'display': 'none' })
        .append($("<div>", { "class": 'part-setting-popup-option', text: vm.$i18n('CCG034_150') })
          .on('click', (event) => {
            vm.onPartClickSetting($newPart, false);
            vm.openPartSettingDialog($newPart);
          }))
        .append($("<div>", { "class": 'part-setting-popup-option', text: vm.$i18n('CCG034_151') })
          .on('click', (event) => {
            vm.onPartClickSetting($newPart, false);
            vm.copyPart($newPart);
          }))
        .append($("<div>", { "class": 'part-setting-popup-option', text: vm.$i18n('CCG034_152') })
          .on('click', (event) => {
            vm.onPartClickSetting($newPart, false);
            vm.removePart($newPart);
          }));
      $partSettingPopup.appendTo($partSetting);
      $partSetting.appendTo($newPart);
      // Check and remove overlap part (both DOM element and data by calling JQuery.remove())
      // vm.filterOverlappingPart(partData); // No need for filter overlap part
      // Add new item to origin list
      vm.$listPart.push($newPart);
      // Append to creation layout
      vm.$menuCreationLayout.append($newPart);
      // Init selectable creation layout
      vm.$menuCreationLayout.selectable({
        selected: (event, ui) => {
          $(ui.selected)
            .addClass("ui-selected")
            .siblings()
            .removeClass("ui-selected");
          // Wait for UI refresh
          vm.$nextTick(() => {
            if ($(ui.selected).hasClass('menu-creation-item-container')) {
              // Only allow dragable + resize on selected menu item
              if ($(ui.selected).hasClass('ui-selected')) {
                $(ui.selected)
                  // Init/enable resize
                  .resizable({
                    disabled: false,
                    resize: (event, ui) => {
                      vm.renderHoveringItemOnResize(ui);
                    },
                    stop: (event, ui) => {
                      vm.$hoverHighlight.remove();
                      vm.resizeItem(ui);
                    },
                  })
                  // Init/enable dragable
                  .draggable({
                    disabled: false,
                    containment: `#${MENU_CREATION_LAYOUT_ID}`,
                    drag: (event, ui) => {
                      const partDataClientId: number = Number(ui.helper.attr(KEY_DATA_ITEM_CLIENT_ID));
                      const partData: PartData = vm.mapPartData[partDataClientId];
                      vm.renderHoveringItemOnDrag(ui, partData.width, partData.height);
                    },
                    stop: (event, ui) => {
                      vm.$hoverHighlight.remove();
                      if (vm.isMouseInsideLayout()) {
                        vm.moveItem(ui);
                      } else {
                        vm.cancelMoveItem(ui);
                      }
                    },
                  });
              } else {
                // Disable dragable + resize on unselected menu item
                $(ui.selected)
                  .resizable({ disabled: true })
                  .draggable({ disabled: true });
              }
            }
          });
        },
        unselected: (event, ui) => {
          // Disable dragable + resize on unselected menu item
          if ($(ui.unselected).hasClass('menu-creation-item-container')) {
            $(ui.unselected)
              .resizable({ disabled: true })
              .draggable({ disabled: true });
            $(ui.unselected)
              .find('.part-setting-popup')
              .css({ 'display': 'none' });
          }
        }
      });
      return $newPart;
    }

    /**
     * Render hovering highlight effect on drag
     * @param item
     */
    private renderHoveringItemOnDrag(item: JQueryUI.DraggableEventUIParams, width: number, height: number) {
      // Parent layout must have position: relative for item.position to be corrected
      const vm = this;
      const partClientId: number = Number(item.helper.attr(KEY_DATA_ITEM_CLIENT_ID));
      // Calculate highlight div position
      const positionTop: number = vm.calculatePositionTop(height, item.position.top);
      const positionLeft: number = vm.calculatePositionLeft(width, item.position.left);
      vm.renderHoveringItem(item.helper, partClientId, width, height, positionTop, positionLeft);
    }

    /**
     * Render hovering highlight effect on resize
     * @param item
     * @param minWidth
     * @param minHeight
     */
    private renderHoveringItemOnResize(item: JQueryUI.ResizableUIParams) {
      // Parent layout must have position: relative for item.position to be corrected
      const vm = this;
      const partClientId: number = Number(item.element.attr(KEY_DATA_ITEM_CLIENT_ID));
      const partData: PartData = vm.mapPartData[partClientId];
      // Calculate highlight div size
      const width: number = item.element.width() > partData.minWidth ? Math.ceil(item.element.width() / CELL_SIZE) * CELL_SIZE : partData.minWidth;
      const height: number = item.element.height() > partData.minHeight ? Math.ceil(item.element.height() / CELL_SIZE) * CELL_SIZE : partData.minHeight;
      vm.renderHoveringItem(item.element, partClientId, width, height, partData.positionTop, partData.positionLeft);
    }

    /**
     * Render hovering highlight effect
     * @param width
     * @param height
     * @param positionTop
     * @param positionLeft
     */
    private renderHoveringItem($originPart: JQuery, partClientId: number, width: number, height: number, positionTop: number, positionLeft: number) {
      const vm = this;
      // If not existed, create new highlight div
      if (!vm.$hoverHighlight) {
        vm.$hoverHighlight = $("<div>", { id: ITEM_HIGHLIGHT_ID, "class": "menu-creation-item-highlight" });
      }
      // Set more attr (highlight width, height, position)
      if (vm.isMouseInsideLayout()) {
        vm.$hoverHighlight
          .outerWidth(width)
          .outerHeight(height)
          .css({
            'visibility': 'visible',
            'top': `${positionTop}px`,
            'left': `${positionLeft}px`
          });
      } else {
        vm.$hoverHighlight.css({
          'visibility': 'hidden',
        });
      }
      // Append to creation layout
      vm.$menuCreationLayout.append(vm.$hoverHighlight);
      // Check overlap
      const overlappingParts: JQuery[] = vm.getOverlappingPart(new PartData({
        clientId: partClientId,
        width: width,
        height: height,
        positionTop: positionTop,
        positionLeft: positionLeft,
      }));
      if (overlappingParts.length) {
        // Overlapped, change cursor to not allow
        $originPart.css({ 'cursor': 'not-allowed' });
        $originPart
          .children('.ui-resizable-e')
          .css({ 'cursor': 'not-allowed' });
        $originPart
          .children('.ui-resizable-s')
          .css({ 'cursor': 'not-allowed' });
        $originPart
          .children('.ui-resizable-se')
          .css({ 'cursor': 'not-allowed' });
      } else {
        // Not overlapped, change cursor to normal
        $originPart.css({ 'cursor': 'auto' });
        $originPart
          .children('.ui-resizable-e')
          .css({ 'cursor': 'e-resize' });
        $originPart
          .children('.ui-resizable-s')
          .css({ 'cursor': 's-resize' });
        $originPart
          .children('.ui-resizable-se')
          .css({ 'cursor': 'se-resize' });
      }
    }

    /**
     * Resize item on stop resizing
     * @param item
     */
    private resizeItem(item: JQueryUI.ResizableUIParams) {
      const vm = this;
      const partClientId: number = Number(item.element.attr(KEY_DATA_ITEM_CLIENT_ID));
      const partData: PartData = vm.mapPartData[partClientId];
      // Calculate highlight div size
      const width: number = item.element.width() > partData.minWidth ? Math.ceil(item.element.width() / CELL_SIZE) * CELL_SIZE : partData.minWidth;
      const height: number = item.element.height() > partData.minHeight ? Math.ceil(item.element.height() / CELL_SIZE) * CELL_SIZE : partData.minHeight;
      // Update width + height
      const resizedPartData = $.extend({}, partData);
      resizedPartData.width = width;
      resizedPartData.height = height;
      // Check overlap
      const overlappingParts: JQuery[] = vm.getOverlappingPart(resizedPartData);
      if (overlappingParts.length) {
        // Revert
        item.element.css({ 'cursor': 'auto' });
        item.element
          .children('.ui-resizable-e')
          .css({ 'cursor': 'e-resize' });
        item.element
          .children('.ui-resizable-s')
          .css({ 'cursor': 's-resize' });
        item.element
          .children('.ui-resizable-se')
          .css({ 'cursor': 'se-resize' });
        vm.cancelResizeItem(item);
      } else {
        // Update part data to map, Update part DOM, Check and remove overlap part (both DOM element and data by calling JQuery.remove())
        vm.mapPartData[partClientId] = resizedPartData;
        vm.renderPartDOM(item.element, resizedPartData.partType, resizedPartData);
        // vm.filterOverlappingPart(resizedPartData); // No need for filter overlap part
      }
    }

    /**
     * Move item on stop dragging
     * @param item
     */
    private moveItem(item: JQueryUI.DraggableEventUIParams) {
      const vm = this;
      const partClientId: number = Number(item.helper.attr(KEY_DATA_ITEM_CLIENT_ID));
      const partData: PartData = vm.mapPartData[partClientId];
      // Calculate highlight div position
      const positionTop: number = vm.calculatePositionTop(partData.height, item.position.top);
      const positionLeft: number = vm.calculatePositionLeft(partData.width, item.position.left);
      // Update positionTop + positionLeft
      const movedPartData = $.extend({}, partData);
      movedPartData.positionTop = positionTop;
      movedPartData.positionLeft = positionLeft;
      // Check overlap
      const overlappingParts: JQuery[] = vm.getOverlappingPart(movedPartData);
      if (overlappingParts.length) {
        // Revert
        item.helper.css({ 'cursor': 'auto' });
        item.helper
          .children('.ui-resizable-e')
          .css({ 'cursor': 'e-resize' });
        item.helper
          .children('.ui-resizable-s')
          .css({ 'cursor': 's-resize' });
        item.helper
          .children('.ui-resizable-se')
          .css({ 'cursor': 'se-resize' });
        vm.cancelMoveItem(item);
      } else {
        // Update part data to map, Update part DOM, Check and remove overlap part (both DOM element and data by calling JQuery.remove())
        vm.mapPartData[partClientId] = movedPartData;
        vm.renderPartDOM(item.helper, movedPartData.partType, movedPartData);
        // vm.filterOverlappingPart(resizedPartData); // No need for filter overlap part
      }
    }

    /**
     * Cancel resize item
     */
    private cancelResizeItem(item: JQueryUI.ResizableUIParams) {
      const vm = this;
      const partClientId: number = Number(item.element.attr(KEY_DATA_ITEM_CLIENT_ID));
      const partData: PartData = vm.mapPartData[partClientId];
      // Update part data to map
      // vm.mapPartData[partClientId] = partData;
      // Update part DOM
      vm.renderPartDOM(item.element, partData.partType, partData);
    }

    /**
     * Cancel move item
     */
    private cancelMoveItem(item: JQueryUI.DraggableEventUIParams) {
      const vm = this;
      const partClientId: number = Number(item.helper.attr(KEY_DATA_ITEM_CLIENT_ID));
      const partData: PartData = vm.mapPartData[partClientId];
      // Update part data to map
      // vm.mapPartData[partClientId] = partData;
      // Update part DOM
      vm.renderPartDOM(item.helper, partData.partType, partData);
    }

    /**
     * Check and remove overlapping part from creation layout
     * @param checkingPart
     */
    private filterOverlappingPart(checkingPart: PartData) {
      const vm = this;
      // Check and remove overlap part (both DOM element and data by calling JQuery.remove())
      const overlappingParts: JQuery[] = vm.getOverlappingPart(checkingPart);
      _.forEach(overlappingParts, (part) => part.remove());
      // Filter overlap part reference from origin list
      vm.$listPart = _.filter(vm.$listPart, ($part) => {
        const partClientId: number = Number($part.attr(KEY_DATA_ITEM_CLIENT_ID));
        return !vm.isItemOverlapping(checkingPart, vm.mapPartData[partClientId]);
      });
    }

    /**
     * Get overlapping Part
     */
    private getOverlappingPart(checkingPart: PartData): JQuery[] {
      const vm = this;
      // Check and remove overlap part (both DOM element and data by calling JQuery.remove())
      return _.filter(vm.$listPart, ($part) => {
        const partClientId: number = Number($part.attr(KEY_DATA_ITEM_CLIENT_ID));
        return vm.isItemOverlapping(checkingPart, vm.mapPartData[partClientId]);
      });
    }

    /**
     * Detects if two item part are colliding
     * https://gist.github.com/jtsternberg/c272d7de5b967cec2d3d
     * @param partData1
     * @param partData2
     */
    private isItemOverlapping(partData1: PartData, partData2: PartData): boolean {
      if (partData1.clientId === partData2.clientId) {
        return false;
      }
      // Part data 1
      const partData1DistanceFromTop = partData1.positionTop + partData1.height;
      const partData1DistanceFromLeft = partData1.positionLeft + partData1.width;
      // Part data 2
      const partData2DistanceFromTop = partData2.positionTop + partData2.height;
      const partData2DistanceFromLeft = partData2.positionLeft + partData2.width;

      const notColliding = (partData1DistanceFromTop <= partData2.positionTop
        || partData1.positionTop >= partData2DistanceFromTop
        || partData1DistanceFromLeft <= partData2.positionLeft
        || partData1.positionLeft >= partData2DistanceFromLeft);

      // Return whether it IS colliding
      return !notColliding;
    }

    /**
     * Get part size by type
     * @param partType
     */
    private getPartSize(partType: string): PartSize {
      switch (partType) {
        case MenuPartType.PART_MENU:
          // 4 x 2 cell
          return new PartSize({ width: 160, height: 80 });
        case MenuPartType.PART_LABEL:
          // 4 x 2 cell
          return new PartSize({ width: 160, height: 80 });
        case MenuPartType.PART_LINK:
          // 4 x 2 cell
          return new PartSize({ width: 160, height: 80 });
        case MenuPartType.PART_ATTACHMENT:
          // 4 x 2 cell
          return new PartSize({ width: 160, height: 80 });
        case MenuPartType.PART_IMAGE:
          // 2 x 2 cell
          return new PartSize({ width: 80, height: 80 });
        case MenuPartType.PART_ARROW:
          // 2 x 2 cell
          return new PartSize({ width: 80, height: 80 });
        default:
          // 4 x 2 cell
          return new PartSize({ width: 160, height: 80 });
      }
    }

    /**
     * Create default part data
     * @param partType
     * @param partSize
     * @param positionTop
     * @param positionLeft
     */
    private createDefaultPartData(partType: string, partSize: PartSize, positionTop: number, positionLeft: number): PartData {
      const vm = this;
      let newPartData: PartData = null;
      switch (partType) {
        case MenuPartType.PART_MENU:
          newPartData = new PartDataMenu({
            // PartData
            clientId: vm.partClientId,
            width: partSize.width,
            height: partSize.height,
            minWidth: CELL_SIZE,
            minHeight: CELL_SIZE,
            partType: partType,
            positionTop: positionTop,
            positionLeft: positionLeft,
            // PartDataMenu
          });
          break;
        case MenuPartType.PART_LABEL:
          newPartData = new PartDataLabel({
            // PartData
            clientId: vm.partClientId,
            width: partSize.width,
            height: partSize.height,
            minWidth: CELL_SIZE,
            minHeight: CELL_SIZE,
            partType: partType,
            positionTop: positionTop,
            positionLeft: positionLeft,
            // PartDataLabel
          });
          break;
        case MenuPartType.PART_LINK:
          newPartData = new PartDataLink({
            // PartData
            clientId: vm.partClientId,
            width: partSize.width,
            height: partSize.height,
            minWidth: CELL_SIZE,
            minHeight: CELL_SIZE,
            partType: partType,
            positionTop: positionTop,
            positionLeft: positionLeft,
            // PartDataLink
          });
          break;
        case MenuPartType.PART_ATTACHMENT:
          newPartData = new PartDataAttachment({
            // PartData
            clientId: vm.partClientId,
            width: partSize.width,
            height: partSize.height,
            minWidth: CELL_SIZE,
            minHeight: CELL_SIZE,
            partType: partType,
            positionTop: positionTop,
            positionLeft: positionLeft,
            // PartDataAttachment
          });
          break;
        case MenuPartType.PART_IMAGE:
          newPartData = new PartDataImage({
            // PartData
            clientId: vm.partClientId,
            width: partSize.width,
            height: partSize.height,
            minWidth: CELL_SIZE,
            minHeight: CELL_SIZE,
            partType: partType,
            positionTop: positionTop,
            positionLeft: positionLeft,
            // PartDataImage
          });
          break;
        case MenuPartType.PART_ARROW:
          newPartData = new PartDataArrow({
            // PartData
            clientId: vm.partClientId,
            width: partSize.width,
            height: partSize.height,
            minWidth: CELL_SIZE,
            minHeight: CELL_SIZE,
            partType: partType,
            positionTop: positionTop,
            positionLeft: positionLeft,
            // PartDataArrow
          });
          break;
        default:
          newPartData = new PartDataMenu({
            // PartData
            clientId: vm.partClientId,
            width: partSize.width,
            height: partSize.height,
            minWidth: CELL_SIZE,
            minHeight: CELL_SIZE,
            partType: partType,
            positionTop: positionTop,
            positionLeft: positionLeft,
            // PartDataMenu
          });
          break;
      }
      // Set part data to map
      vm.mapPartData[vm.partClientId] = newPartData;
      vm.partClientId++;
      return newPartData;
    }

    /**
     * Copy part data
     */
    private copyPartData(originPartData: PartData, positionTop: number, positionLeft: number): PartData {
      const vm = this;
      let newPartData: PartData = null;
      switch (originPartData.partType) {
        case MenuPartType.PART_MENU:
          newPartData = new PartDataMenu(originPartData);
          break;
        case MenuPartType.PART_LABEL:
          newPartData = new PartDataLabel(originPartData);
          break;
        case MenuPartType.PART_LINK:
          newPartData = new PartDataLink(originPartData);
          break;
        case MenuPartType.PART_ATTACHMENT:
          newPartData = new PartDataAttachment(originPartData);
          break;
        case MenuPartType.PART_IMAGE:
          newPartData = new PartDataImage(originPartData);
          break;
        case MenuPartType.PART_ARROW:
          newPartData = new PartDataArrow(originPartData);
          break;
        default:
          newPartData = new PartDataMenu(originPartData);
          break;
      }
      // Set part data to map
      newPartData.clientId = vm.partClientId;
      newPartData.positionTop = positionTop;
      newPartData.positionLeft = positionLeft;
      vm.mapPartData[vm.partClientId] = newPartData;
      vm.partClientId++;
      return newPartData;
    }

    /**
     * Create part class
     * @param partType
     */
    private renderPartDOM($part: JQuery, partType: string, partData: PartData): JQuery {
      const vm = this;
      switch (partType) {
        case MenuPartType.PART_MENU:
          return vm.renderPartDOMMenu($part, partData as PartDataMenu);
        case MenuPartType.PART_LABEL:
          return vm.renderPartDOMLabel($part, partData as PartDataLabel);
        case MenuPartType.PART_LINK:
          return vm.renderPartDOMLink($part, partData as PartDataLink);
        case MenuPartType.PART_ATTACHMENT:
          return vm.renderPartDOMAttachment($part, partData as PartDataAttachment);
        case MenuPartType.PART_IMAGE:
          return vm.renderPartDOMImage($part, partData as PartDataImage);
        case MenuPartType.PART_ARROW:
          return vm.renderPartDOMArrow($part, partData as PartDataArrow);
        default:
          return vm.renderPartDOMMenu($part, partData as PartDataMenu);
      }
    }

    /**
     * Render PartDataMenu
     * @param partData
     */
    private renderPartDOMMenu($partContainer: JQuery, partData: PartDataMenu): JQuery {
      const vm = this;
      $partContainer
        // Set PartData attr
        .outerWidth(partData.width)
        .outerHeight(partData.height)
        .css({
          'top': `${partData.positionTop}px`,
          'left': `${partData.positionLeft}px`,
        })
        // Update item data object
        .attr(KEY_DATA_ITEM_CLIENT_ID, partData.clientId);
      const $part = $partContainer.find('.menu-creation-item');
      $part
        // Set PartDataLabel attr
        .css({
          'display': 'flex',
          'justify-content': vm.getHorizontalClass(partData.alignHorizontal),
          'align-items': vm.getVerticalClass(partData.alignVertical),
        });
      // Render label
      let $menuName = $part.find('.part-menu-name');
      if (!$menuName.length) {
        $menuName = $("<span>", { 'class': 'part-menu-name' });
      }
      $menuName
        .text(partData.menuName)
        .css({
          'font-size': partData.fontSize,
          'font-weight': partData.isBold ? 'bold' : 'normal',
        })
        .addClass('hyperlink');
      $menuName.appendTo($part);
      return $partContainer;
    }

    /**
     * Render PartDataLabel
     * @param partData
     */
    private renderPartDOMLabel($partContainer: JQuery, partData: PartDataLabel): JQuery {
      const vm = this;
      $partContainer
        // Set PartData attr
        .outerWidth(partData.width)
        .outerHeight(partData.height)
        .css({
          'top': `${partData.positionTop}px`,
          'left': `${partData.positionLeft}px`,
        })
        // Update item data object
        .attr(KEY_DATA_ITEM_CLIENT_ID, partData.clientId);
      const $part = $partContainer.find('.menu-creation-item');
      $part
        // Set PartDataLabel attr
        .css({
          'color': partData.textColor,
          'background-color': partData.backgroundColor,
          'display': 'flex',
          'justify-content': vm.getHorizontalClass(partData.alignHorizontal),
          'align-items': vm.getVerticalClass(partData.alignVertical),
        });
      // Render label
      let $labelContent = $part.find('.part-label-content');
      if (!$labelContent.length) {
        $labelContent = $("<span>", { 'class': 'part-label-content' });
      }
      $labelContent
        .text(partData.labelContent)
        .css({
          'font-size': partData.fontSize,
          'font-weight': partData.isBold ? 'bold' : 'normal',
        });
      $labelContent.appendTo($part);
      return $partContainer;
    }

    /**
     * Render PartDataLink
     * @param partData
     */
    private renderPartDOMLink($partContainer: JQuery, partData: PartDataLink): JQuery {
      const vm = this;
      $partContainer
        // Set PartData attr
        .outerWidth(partData.width)
        .outerHeight(partData.height)
        .css({
          'top': `${partData.positionTop}px`,
          'left': `${partData.positionLeft}px`,
        })
        // Update item data object
        .attr(KEY_DATA_ITEM_CLIENT_ID, partData.clientId);
      const $part = $partContainer.find('.menu-creation-item');
      $part
        // Set PartDataLabel attr
        .css({
          'display': 'flex',
          'justify-content': vm.getHorizontalClass(partData.alignHorizontal),
          'align-items': vm.getVerticalClass(partData.alignVertical),
        });
      // Render label
      let $linkContent = $part.find('.part-link-content');
      if (!$linkContent.length) {
        $linkContent = $("<span>", { 'class': 'part-link-content' });
      }
      $linkContent
        .text(partData.linkContent || partData.url)
        .css({
          'font-size': partData.fontSize,
          'font-weight': partData.isBold ? 'bold' : 'normal',
        })
        .addClass('hyperlink');
      $linkContent.appendTo($part);
      return $partContainer;
    }

    /**
     * Render PartDataAttachment
     * @param partData
     */
    private renderPartDOMAttachment($partContainer: JQuery, partData: PartDataAttachment): JQuery {
      const vm = this;
      $partContainer
        // Set PartData attr
        .outerWidth(partData.width)
        .outerHeight(partData.height)
        .css({
          'top': `${partData.positionTop}px`,
          'left': `${partData.positionLeft}px`,
        })
        // Update item data object
        .attr(KEY_DATA_ITEM_CLIENT_ID, partData.clientId);
      const $part = $partContainer.find('.menu-creation-item');
      $part
        // Set PartDataLabel attr
        .css({
          'display': 'flex',
          'justify-content': vm.getHorizontalClass(partData.alignHorizontal),
          'align-items': vm.getVerticalClass(partData.alignVertical),
        });
      // Render label
      let $fileContent = $part.find('.part-file-content');
      if (!$fileContent.length) {
        $fileContent = $("<span>", { 'class': 'part-file-content' });
      }
      $fileContent
        .text(partData.linkContent)
        .css({
          'font-size': partData.fontSize,
          'font-weight': partData.isBold ? 'bold' : 'normal',
        })
        .addClass('hyperlink');
      $fileContent.appendTo($part);
      return $partContainer;
    }

    /**
     * Render PartDataImage
     * @param partData
     */
    private renderPartDOMImage($partContainer: JQuery, partData: PartDataImage): JQuery {
      $partContainer
        // Set PartData attr
        .outerWidth(partData.width)
        .outerHeight(partData.height)
        .css({
          'top': `${partData.positionTop}px`,
          'left': `${partData.positionLeft}px`,
          'align-items': 'center'
        })
        // Update item data object
        .attr(KEY_DATA_ITEM_CLIENT_ID, partData.clientId);
      const $part = $partContainer.find('.menu-creation-item');
      $part
        // Set PartDataLabel attr
        .css({
          'display': 'flex',
        });
      // Render label
      let $imageContent = $part.find('.part-image-content');
      if (!$imageContent.length) {
        $imageContent = $("<img>", { 'class': 'part-image-content' });
      }
      $imageContent
        .attr('src', (partData.isFixed === 0) ? partData.fileName : (nts.uk.request as any).liveView(partData.fileId));
      // Set image scale by original ratio
      const partRatio = partData.height / partData.width;
      const imageRatio = partData.ratio;
      if (partRatio > imageRatio) {
        $imageContent.css({
          'width': '100%',
          'height': 'auto',
        });
      } else {
        $imageContent.css({
          'width': 'auto',
          'height': '100%',
        });
      }
      $imageContent.appendTo($part);
      return $partContainer;
    }

    /**
     * Render PartDataArrow
     * @param partData
     */
    private renderPartDOMArrow($partContainer: JQuery, partData: PartDataArrow): JQuery {
      $partContainer
        // Set PartData attr
        .outerWidth(partData.width)
        .outerHeight(partData.height)
        .css({
          'top': `${partData.positionTop}px`,
          'left': `${partData.positionLeft}px`,
        })
        // Update item data object
        .attr(KEY_DATA_ITEM_CLIENT_ID, partData.clientId);
      const $part = $partContainer.find('.menu-creation-item');
      $part
        // Set PartDataLabel attr
        .css({
          'display': 'flex',
        });
      // Render label
      let $arrowContent = $part.find('.part-arrow-content');
      if (!$arrowContent.length) {
        $arrowContent = $("<img>", { 'class': 'part-arrow-content' });
      }
      $arrowContent.attr('src', partData.fileSrc);
      // Set image scale by original ratio
      const partRatio = partData.height / partData.width;
      const imageRatio = 1;
      if (partRatio > imageRatio) {
        $arrowContent.css({
          'width': '100%',
          'height': 'auto',
        });
      } else {
        $arrowContent.css({
          'width': 'auto',
          'height': '100%',
        });
      }
      $arrowContent.appendTo($part);
      return $partContainer;
    }

    /**
     * getHorizontalClass
     */
    private getHorizontalClass(alignHorizontal: number): string {
      let horizontalPosition: string = 'flex-start';
      switch (alignHorizontal) {
        case HorizontalAlign.LEFT:
          horizontalPosition = 'flex-start';
          break;
        case HorizontalAlign.MIDDLE:
          horizontalPosition = 'center';
          break;
        case HorizontalAlign.RIGHT:
          horizontalPosition = 'flex-end';
          break;
        default:
          horizontalPosition = 'flex-start';
          break;
      }
      return horizontalPosition;
    }

    /**
     * getVerticalClass
     */
    private getVerticalClass(alignVertical: number): string {
      let verticalPosition: string = 'flex-start';
      switch (alignVertical) {
        case VerticalAlign.TOP:
          verticalPosition = 'flex-start';
          break;
        case VerticalAlign.CENTER:
          verticalPosition = 'center';
          break;
        case VerticalAlign.BOTTOM:
          verticalPosition = 'flex-end';
          break;
        default:
          verticalPosition = 'flex-start';
          break;
      }
      return verticalPosition;
    }

    /**
     * On click part setting
     * @param partClientId
     */
    private onPartClickSetting($part: JQuery, visible: boolean) {
      const $partSettingPopup: JQuery = $part.find('.part-setting-popup');
      if ($partSettingPopup) {
        if (visible) {
          $partSettingPopup.css('display', 'initial');
        } else {
          $partSettingPopup.css('display', 'none');
        }
      }
    }

    /**
     * Open Part Setting Dialog
     * @param partClientId
     */
    private openPartSettingDialog($part: JQuery, isCreateDialog?: boolean) {
      const vm = this;
      const partClientId: number = Number($part.attr(KEY_DATA_ITEM_CLIENT_ID));
      const selectedPartData: PartData = vm.mapPartData[partClientId];
      if (selectedPartData) {
        switch (selectedPartData.partType) {
          case MenuPartType.PART_MENU:
            vm.$window.modal('/view/ccg/034/f/index.xhtml', selectedPartData)
              .then((result: PartData) => {
                if (result) {
                  // Update part data
                  vm.mapPartData[partClientId] = result;
                  // Update part DOM
                  vm.renderPartDOMMenu($part, result as PartDataMenu);
                } else {
                  if (isCreateDialog) {
                    // If this is dialog setitng when create => remove part
                    vm.removePart($part);
                  }
                }
              });
            break;
          case MenuPartType.PART_LABEL:
            vm.$window.modal('/view/ccg/034/e/index.xhtml', selectedPartData)
              .then((result: PartData) => {
                if (result) {
                  // Update part data
                  vm.mapPartData[partClientId] = result;
                  // Update part DOM
                  vm.renderPartDOMLabel($part, result as PartDataLabel);
                } else {
                  if (isCreateDialog) {
                    // If this is dialog setitng when create => remove part
                    vm.removePart($part);
                  }
                }
              });
            break;
          case MenuPartType.PART_LINK:
            vm.$window.modal('/view/ccg/034/g/index.xhtml', selectedPartData)
              .then((result: PartData) => {
                if (result) {
                  // Update part data
                  vm.mapPartData[partClientId] = result;
                  // Update part DOM
                  vm.renderPartDOMLink($part, result as PartDataLink);
                } else {
                  if (isCreateDialog) {
                    // If this is dialog setitng when create => remove part
                    vm.removePart($part);
                  }
                }
              });
            break;
          case MenuPartType.PART_ATTACHMENT:
            vm.$window.modal('/view/ccg/034/h/index.xhtml', selectedPartData)
              .then((result: PartData) => {
                if (result) {
                  // Update part data
                  vm.mapPartData[partClientId] = result;
                  // Update part DOM
                  vm.renderPartDOMAttachment($part, result as PartDataAttachment);
                } else {
                  if (isCreateDialog) {
                    // If this is dialog setitng when create => remove part
                    vm.removePart($part);
                  }
                }
              });
            break;
          case MenuPartType.PART_IMAGE:
            vm.$window.modal('/view/ccg/034/i/index.xhtml', selectedPartData)
              .then((result: PartData) => {
                if (result) {
                  // Update part data
                  vm.mapPartData[partClientId] = result;
                  // Update part DOM
                  vm.renderPartDOMImage($part, result as PartDataImage);
                } else {
                  if (isCreateDialog) {
                    // If this is dialog setitng when create => remove part
                    vm.removePart($part);
                  }
                }
              });
            break;
          case MenuPartType.PART_ARROW:
            vm.$window.modal('/view/ccg/034/j/index.xhtml', selectedPartData)
              .then((result: PartData) => {
                if (result) {
                  // Update part data
                  vm.mapPartData[partClientId] = result;
                  // Update part DOM
                  vm.renderPartDOMArrow($part, result as PartDataArrow);
                } else {
                  if (isCreateDialog) {
                    // If this is dialog setitng when create => remove part
                    vm.removePart($part);
                  }
                }
              });
            break;
          default:
            break;
        }
      }
    }

    /**
     * Copy part
     * @param $part
     */
    private copyPart($part: JQuery) {
      const vm = this;
      const partClientId: number = Number($part.attr(KEY_DATA_ITEM_CLIENT_ID));
      const partData = vm.mapPartData[partClientId];
      // Start copy mode
      vm.isCopying(true);
      vm.copyingPartId(partClientId);
      // Create new placeholder div
      switch (partData.partType) {
        case MenuPartType.PART_MENU:
          vm.$copyPlaceholder = $("<div>", { id: ITEM_COPY_PLACEHOLDER_ID, "class": 'menu-creation-item-copy-placeholder' })
            .append($('<div>', { 'class': 'menu-creation-item part-menu' }));
          break;
        case MenuPartType.PART_LABEL:
          vm.$copyPlaceholder = $("<div>", { id: ITEM_COPY_PLACEHOLDER_ID, "class": 'menu-creation-item-copy-placeholder' })
            .append($('<div>', { 'class': 'menu-creation-item part-label' }));
          break;
        case MenuPartType.PART_LINK:
          vm.$copyPlaceholder = $("<div>", { id: ITEM_COPY_PLACEHOLDER_ID, "class": 'menu-creation-item-copy-placeholder' })
            .append($('<div>', { 'class': 'menu-creation-item part-link' }));
          break;
        case MenuPartType.PART_ATTACHMENT:
          vm.$copyPlaceholder = $("<div>", { id: ITEM_COPY_PLACEHOLDER_ID, "class": 'menu-creation-item-copy-placeholder' })
            .append($('<div>', { 'class': 'menu-creation-item part-attachment' }));
          break;
        case MenuPartType.PART_IMAGE:
          vm.$copyPlaceholder = $("<div>", { id: ITEM_COPY_PLACEHOLDER_ID, "class": 'menu-creation-item-copy-placeholder' })
            .append($('<div>', { 'class': 'menu-creation-item part-image' }));
          break;
        case MenuPartType.PART_ARROW:
          vm.$copyPlaceholder = $("<div>", { id: ITEM_COPY_PLACEHOLDER_ID, "class": 'menu-creation-item-copy-placeholder' })
            .append($('<div>', { 'class': 'menu-creation-item part-arrow' }));
          break;
        default:
          vm.$copyPlaceholder = $("<div>", { id: ITEM_COPY_PLACEHOLDER_ID, "class": 'menu-creation-item-copy-placeholder' })
            .append($('<div>', { 'class': 'menu-creation-item part-menu' }));
          break;
      }
      // Set more attr (highlight width, height, position)
      vm.renderPartDOM(vm.$copyPlaceholder, partData.partType, partData);
      // Append to creation layout
      vm.$menuCreationLayout.append(vm.$copyPlaceholder);
      // Move placeholder on mouse move
      const layoutOffset = vm.$menuCreationLayout.offset();
      vm.layoutOffsetTop(layoutOffset.top);
      vm.layoutOffsetLeft(layoutOffset.left);
      vm.$menuCreationLayout.mousemove((event) => {
        const offsetX = event.pageX - layoutOffset.left + vm.$menuCreationLayoutContainer.scrollLeft();
        const offsetY = event.pageY - layoutOffset.top + vm.$menuCreationLayoutContainer.scrollTop();
        vm.$copyPlaceholder.css({ 'top': `${offsetY}px`, 'left': `${offsetX}px` });
        // Calculate highlight div position
        const positionTop: number = vm.calculatePositionTop(partData.height, offsetY);
        const positionLeft: number = vm.calculatePositionLeft(partData.width, offsetX);
        vm.renderHoveringItem(vm.$copyPlaceholder, vm.partClientId, partData.width, partData.height, positionTop, positionLeft);
      });
    }

    /**
     * Remove part
     */
    private removePart($part: JQuery) {
      const vm = this;
      const partClientId: number = Number($part.attr(KEY_DATA_ITEM_CLIENT_ID));
      delete vm.mapPartData[partClientId];
      vm.$listPart = _.filter(vm.$listPart, ($item) => Number($item.attr(KEY_DATA_ITEM_CLIENT_ID)) !== partClientId);
      $part.remove();
    }

    /**
     * Close dialog
     */
    public closeDialog() {
      const vm = this;
      vm.$window.close();
    }

    /**
     * Open preview dialog
     */
    public openPreviewDialog() {
      // TODO
    }

    /**
     * Save layout
     */
    public saveLayout() {
      // TODO
    }

    private calculatePositionTop(itemHeight: number, positionTop: number): number {
      const maxPositionTop = CREATION_LAYOUT_HEIGHT - itemHeight;
      return (positionTop > 0)
        ? ((positionTop + itemHeight) <= CREATION_LAYOUT_HEIGHT ? Math.round(positionTop / CELL_SIZE) : maxPositionTop / CELL_SIZE) * CELL_SIZE
        : 0;
    }

    private calculatePositionLeft(itemWidth: number, positionLeft: number): number {
      const maxPositionLeft = CREATION_LAYOUT_WIDTH - itemWidth;
      return (positionLeft > 0)
        ? ((positionLeft + itemWidth) <= CREATION_LAYOUT_WIDTH ? Math.round(positionLeft / CELL_SIZE) : maxPositionLeft / CELL_SIZE) * CELL_SIZE
        : 0;
    }

  }

  export class PartData {
    clientId: number;
    width: number;
    height: number;
    minWidth: number;
    minHeight: number;
    partType: string;
    positionTop: number;
    positionLeft: number;

    constructor(init?: Partial<PartData>) {
      $.extend(this, init);
    }
  }

  export class PartDataMenu extends PartData {
    alignHorizontal: number = HorizontalAlign.MIDDLE;
    alignVertical: number = VerticalAlign.CENTER;
    menuCode: string = null;
    menuName: string = "";
    menuClassification: number = 0;
    systemType: number = 0;
    fontSize: number = 11;
    isBold: boolean = true;

    constructor(init?: Partial<PartDataMenu>) {
      super(init);
      $.extend(this, init);
    }
  }

  export class PartDataLabel extends PartData {
    // Default data
    alignHorizontal: number = HorizontalAlign.LEFT;
    alignVertical: number = VerticalAlign.CENTER;
    labelContent: string = '';
    fontSize: number = 11;
    isBold: boolean = true;
    textColor: string = '#000000';
    backgroundColor: string = '#ffffff';

    constructor(init?: Partial<PartDataMenu>) {
      super(init);
      $.extend(this, init);
    }
  }

  export class PartDataLink extends PartData {
    // Default data
    alignHorizontal: number = HorizontalAlign.LEFT;
    alignVertical: number = VerticalAlign.CENTER;
    url: string = null;
    linkContent: string = '';
    fontSize: number = 11;
    isBold: boolean = true;

    constructor(init?: Partial<PartDataLink>) {
      super(init);
      $.extend(this, init);
    }
  }

  export class PartDataAttachment extends PartData {
    // Default data
    alignHorizontal: number = HorizontalAlign.LEFT;
    alignVertical: number = VerticalAlign.CENTER;
    fileId: string = null;
    fileSize: number = 0;
    fileName: string = null;
    linkContent: string = '';
    fileLink: string = null;
    fontSize: number = 11;
    isBold: boolean = true;

    constructor(init?: Partial<PartDataAttachment>) {
      super(init);
      $.extend(this, init);
    }
  }

  export class PartDataImage extends PartData {
    // Default data
    fileId: string = null;
    fileName: string = null;
    uploadedFileName: string = null;
    uploadedFileSize: number = 0;
    isFixed: number = 0;
    ratio: number = 1;

    constructor(init?: Partial<PartDataImage>) {
      super(init);
      $.extend(this, init);
    }
  }

  export class PartDataArrow extends PartData {
    // Default data
    fileName: string = null;
    fileSrc: string = null

    constructor(init?: Partial<PartDataArrow>) {
      super(init);
      $.extend(this, init);
    }
  }

  class PartSize {
    width: number;
    height: number;

    constructor(init?: Partial<PartSize>) {
      $.extend(this, init);
    }
  }

  enum MenuPartType {
    PART_MENU = '1',
    PART_LABEL = '2',
    PART_LINK = '3',
    PART_ATTACHMENT = '4',
    PART_IMAGE = '5',
    PART_ARROW = '6',
  }

  enum HorizontalAlign {
    LEFT = 0,
    MIDDLE = 1,
    RIGHT = 2,
  }

  enum VerticalAlign {
    TOP = 0,
    CENTER = 1,
    BOTTOM = 2,
  }

  interface ItemModel {
    code: number;
    name: string;
  }

}