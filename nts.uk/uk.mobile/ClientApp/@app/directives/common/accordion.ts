import { dom } from '@app/utils';
import { Vue } from '@app/provider';

const accordion = {
    bind(container: HTMLElement) {
        container.addEventListener('click', (evt: MouseEvent) => {
            let title = evt.target as HTMLElement;

            if (title && title.classList.contains('btn-link')) {
                let card = title.closest('.card') as HTMLElement;

                if (card) {
                    let collapse = card.querySelector('.collapse') as HTMLElement,
                        collapses = container.querySelectorAll('.collapse');

                    if (collapse) {
                        let showOrHide = dom.hasClass(collapse, 'show');

                        [].slice.call(collapses).forEach((element: HTMLElement) => {
                            dom.removeClass(element, 'show');
                        });

                        if (!showOrHide) {
                            dom.addClass(collapse, 'show');
                        }
                    }
                }
            }
        });
    }
};

Vue.directive('nts-accordion', accordion);