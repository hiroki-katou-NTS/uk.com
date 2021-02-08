<template>
<div class="ksus01a">
    <div class="card card-label">
        <!-- A1_1 -->
        <div class="card-header uk-bg-green" style="border-color: #E0F59E">
            <div id="year-month-component" class="flex-center-vert">
                <!-- A1_1_1 -->
                <span v-on:click="changeYearMonth(false)">
                    <i class="fas fa-arrow-alt-circle-left"></i>
                </span>
                <!-- A1_1_2 -->
                <nts-year-month
                    v-bind:showTitle="false"
                    v-model="yearMonth"
                    name="対象月"
                />
                <!-- A1_1_3 -->
                <span v-on:click="changeYearMonth(true)">
                    <i class="fas fa-arrow-alt-circle-right"></i>
                </span>
            </div>
            <div class="flex-center-vert">
                <!-- A1_1_4 -->
                <span 
                    v-on:click="backCurrentMonth()"
                    style="background-color: yellow; padding: 0.5em; margin: 0 1em"
                >
                    {{ "KSUS01_2" | i18n }}
                </span>
                <!-- A1_1_5 -->
                <span v-on:click="openKSUS01B()">
                    <i class="fas fa-info-circle"></i>
                </span>
            </div>
        </div>

        <!-- Calendar -->
        <div class="card-body" style="margin: -0.5rem -1rem 0 -1rem;">
            <div class="date-header-container">
                <!-- A1_2 -->
                <div
                    v-for="(item, index) in dateHeaderList"
                    v-bind:key="index"
                    :value="index"
                    v-bind:class="setHeaderColor(item.weekDayIndex)"
                >
                    <!-- A1_2_1 -->
                    <div>{{item.headerName}}</div>
                </div>
            </div>

            <div class="date-cell-container">
                <!-- A1_3 -->
                <div
                    v-for="(item, index) in dateCellList"
                    v-bind:key="index"
                    :value="index"
                    v-bind:class="setCellColor(item)"
                    v-on:click="showDetail(true, item)"
                    v-on:touchstart="handleTouchStart"
                    v-on:touchend="handleTouchEnd"
                    v-show="rowFocus == null || rowFocus == item.rowNumber"
                >
                    <div v-show="item.isActive" style="height: 100%; display: flex; justify-content: flex-start; flex-direction: column;">
                        <div style="display: flex; justify-content: space-between">
                            <!-- A1_3_1 -->
                            <span v-if="today == item.date" class="uk-bg-schedule-that-day" style="border-radius: 50%;">{{item.formatedDate}}</span>
                            <span v-else>{{item.formatedDate}}</span>
                            <!-- A1_3_2 -->
                            <span v-show="item.workDesire != undefined">
                                <i class="far fa-star uk-text-attendance" v-if="item.workDesire" ></i>
                                <i class="far fa-star uk-text-holiday" v-else></i>
                            </span>
                        </div>
                        <!-- A1_3_3 -->
                        <div style="text-align: center; margin: 2em 0">
                            <span v-show="item.workSchedule != undefined" 
                                style="padding: 0.25em 1em; font-weight: bold; border-radius: 0.25rem; background-color: red"
                            >
                                {{item.workSchedule}}
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- A3 -->
    <div id="detail-component" class="card card-label" v-show="isDetailShow">
        <!-- A3_1 -->
        <div class="card-header uk-bg-choice-row" v-on:click="showDetail(false)">
            <span style="font-weight: bold;">{{detailCell && detailCell.formatedLongMdwDate}}</span>
            <i class="fas fa-sort-down"></i>
            <!-- an invisible span to center the icon with flex -->
            <span style="font-weight: bold; visibility: hidden">{{detailCell && detailCell.formatedLongMdwDate}}</span>
        </div>
        
        <div class="card-body" >
            <div class="detail-spacing">
                <!-- A4_1 -->
                <span><i class="far fa-calendar-alt"></i></span>
                <!-- A4_2 -->
                <span style="color: #999">{{ "KSUS01_10" | i18n }}</span>
            </div>
            <div class="detail-spacing detail-indent">
                <!-- A4_3 -->
                <span style="padding: 0.25em 1em; font-weight: bold; border-radius: 0.25rem; background-color: red">
                    {{detailCell && detailCell.workSchedule}}
                </span>
                <!-- A4_4 -->
                <span style="font-weight: bold;">
                    予定の時間帯１
                </span>
                <!-- A4_5 -->
                <span style="font-weight: bold;">
                    予定の時間帯2
                </span>
            </div>
            <!-- huytodo v-show -->
            <div class="detail-spacing" v-show="'schedule' != 'break'">
                <!-- A4_6 -->
                <span><i class="fa fa-user-alt"></i></span>
                <!-- A4_7 -->
                <span style="color: #999">{{ "KSUS01_11" | i18n }}</span>
            </div>
            <div class="detail-spacing detail-indent" v-show="'schedule' != 'break'">
                <!-- A4_8 -->
                <span style="font-weight: bold;">
                    他のスタップ
                </span>
            </div>
            <!-- A4_9 -->
            <hr/>
            <div class="detail-spacing">
                <!-- A4_10 -->
                <span><i class="far fa-star"></i></span>
                <!-- A4_11 -->
                <span style="color: #999">{{ "KSUS01_12" | i18n }}</span>
            </div>
            <div class="detail-spacing detail-indent">
                <!-- A4_12 -->
                <span style="padding: 0.25em 1em; font-weight: bold; border-radius: 0.25rem; background-color: red">
                    勤務希望
                </span>
                <!-- A4_13 -->
                <span style="font-weight: bold;">
                    希望の時間帯１
                </span>
                <!-- A4_14 -->
                <span style="font-weight: bold;">
                    希望の時間帯2
                </span>
            </div>
            <div class="detail-spacing">
                <!-- A4_15 -->
                <span><i class="far fa-sticky-note"></i></span>
                <!-- A4_16 -->
                <span style="color: #999">{{ "KSUS01_13" | i18n }}</span>
            </div>
            <div class="detail-spacing">
                <!-- A4_17 -->
                <nts-text-area
                    v-bind:disabled="true"
                    v-bind:showTitle="false"
                    v-bind:inlineTitle="false"/>
            </div>
        </div>
    </div>
</div>
</template>