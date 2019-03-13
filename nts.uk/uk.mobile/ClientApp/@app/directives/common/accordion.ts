import { dom } from '@app/utils';
import { Vue } from '@app/provider';

const accordion = {
    bind(container: HTMLElement) {
        // Bắt sự kiện click vào element đặt directive
        container.addEventListener('click', (evt: MouseEvent) => {
            let title = evt.target as HTMLElement;

            // Tìm title của các group, nếu thấy và đúng định dạng
            if (title && title.classList.contains('btn-link')) {
                let card = title.closest('.card') as HTMLElement;

                // tìm thẻ chứa cả title và body của một collapse
                if (card) {
                    // nếu thấy thì tìm tất cả các body khác
                    let collapse = card.querySelector('.collapse') as HTMLElement,
                        collapses = container.querySelectorAll('.collapse');

                    if (collapse) {
                        // kiểm tra trạng thái đang đóng hay mở
                        let showOrHide = dom.hasClass(collapse, 'show');

                        // đóng tất cả các body khác
                        [].slice.call(collapses).forEach((element: HTMLElement) => {
                            dom.removeClass(element, 'show');
                        });

                        // nếu là trạng thái đóng thì mở lại body được click (bằng title)
                        if (!showOrHide) {
                            dom.addClass(collapse, 'show');
                        }
                    }
                }
            }
        });
    }
};

// khai báo directive là dạng global
Vue.directive('nts-accordion', accordion);