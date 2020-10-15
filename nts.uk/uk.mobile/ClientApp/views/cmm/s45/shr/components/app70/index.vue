<template>
    <div class="cmms45shrcomponentsapp70">
        <div v-if="listWorkHours.length > 0 || listTempoHours.length > 0">
            <!-- B1 -->
            <div class="card card-label">
                <div class="card-header uk-bg-accordion mt-2">
                <span>{{ "KAFS02_3" | i18n }}</span>
                </div>
            </div>

            <!-- workHours -->
            <div>
                <div v-for="itemWH in listWorkHours" :key="itemWH.frame">
                    <div class="row">
                        <!-- B1_1 -->
                        <div class="col-6">{{ itemWH.title | i18n }}</div>
                    </div>

                    <div class="row">
                        <!-- B1_2 -->
                        <div class="col-6 text-left" style="font-size: 80%"><kafs00subp3 v-bind:params="itemWH.appHours" /></div>

                        <!-- B1_3 -->
                        <div class="col-6 text-right" style="font-size: 80%"><kafs00subp3 v-bind:params="itemWH.actualHours" /></div>
                    </div>

                    <div v-if="1 < listWorkHours[listWorkHours.length - 1].frame"><hr/></div>
                </div>
            </div>

            <!-- tempoHours -->
            <div>
                <div v-for="itemTH in listTempoHours" :key="itemTH.frame">
                    <div class="row">
                        <!-- B1_1 -->
                        <div class="col-6">{{ itemTH.title | i18n(itemTH.frame) }}</div>
                    </div>

                    <div class="row">
                        <!-- B1_2 -->
                        <div class="col-6 text-left" style="font-size: 80%"><kafs00subp3 v-bind:params="itemTH.appHours" /></div>

                        <!-- B1_3 -->
                        <div class="col-6 text-right" style="font-size: 80%"><kafs00subp3 v-bind:params="itemTH.actualHours" /></div>
                    </div>

                    <div v-if="itemTH.frame < listTempoHours[listTempoHours.length - 1].frame"><hr/></div>
                </div>
            </div>
        </div>

        <!-- goOutHours -->
        <div v-if="listOutingHours.length > 0">
            <!-- B2 -->
            <div class="card card-label">
                <div class="card-header uk-bg-accordion mt-2">
                <span>{{ "KAFS02_8" | i18n }}</span>
                </div>
            </div>

            <!-- goOutHours -->
            <div>
                <div v-for="itemGH in listOutingHours" :key="itemGH.frame">
                    <div class="row">
                        <!-- B2_1 -->
                        <div class="col-6">
                            <span>{{ itemGH.title | i18n(itemGH.frame) }}</span>
                            <span>{{ itemGH.outingType | i18n }}</span>
                        </div>
                    </div>

                    <div class="row">
                        <!-- B2_2 -->
                        <div class="col-6 text-left" style="font-size: 80%"><kafs00subp3 v-bind:params="itemGH.appHours" /></div>

                        <!-- B2_3 -->
                        <div class="col-6 text-right" style="font-size: 80%"><kafs00subp3 v-bind:params="itemGH.actualHours" /></div>
                    </div>

                    <div v-if="itemGH.frame < listOutingHours[listOutingHours.length - 1].frame"><hr/></div>
                </div>
            </div>
        </div>

        <!-- breakHours -->
        <div v-if="listBreakHours.length > 0">
            <!-- B2 -->
            <div class="card card-label">
                <div class="card-header uk-bg-accordion mt-2">
                <span>{{ "KAFS02_11" | i18n }}</span>
                </div>
            </div>

            <!-- breakHours -->
            <div>
                <div v-for="itemBH in listBreakHours" :key="itemBH.frame">
                    <div class="row">
                        <!-- B3_1 -->
                        <div class="col-6">
                            <span>{{ itemBH.title | i18n(itemBH.frame) }}</span>
                        </div>
                    </div>

                    <div class="row">
                        <!-- B3_2 -->
                        <div class="col-6 text-left" style="font-size: 80%"><kafs00subp3 v-bind:params="itemBH.appHours" /></div>

                        <!-- B3_3 -->
                        <div class="col-6 text-right" style="font-size: 80%"><kafs00subp3 v-bind:params="itemBH.actualHours" /></div>
                    </div>

                    <div v-if="itemBH.frame < listBreakHours[listBreakHours.length - 1].frame"><hr/></div>
                </div>
            </div>
        </div>

        <!-- chidcareHours -->
        <div v-if="listParentHours.length > 0">
            <!-- B4 -->
            <div class="card card-label">
                <div class="card-header uk-bg-accordion mt-2">
                <span>{{ "KAFS02_13" | i18n }}</span>
                </div>
            </div>

            <!-- chidcareHours -->
            <div>
                <div v-for="itemCH in listParentHours" :key="itemCH.frame">
                    <div class="row">
                        <!-- B4_1 -->
                        <div class="col-6">
                            <span>{{ itemCH.title | i18n(itemCH.frame) }}</span>
                        </div>
                    </div>

                    <div class="row">
                        <!-- B4_2 -->
                        <div class="col-6 text-left" style="font-size: 80%"><kafs00subp3 v-bind:params="itemCH.appHours" /></div>

                        <!-- B4_3 -->
                        <div class="col-6 text-right" style="font-size: 80%"><kafs00subp3 v-bind:params="itemCH.actualHours" /></div>
                    </div>

                    <div v-if="itemCH.frame < listParentHours[listParentHours.length - 1].frame"><hr/></div>
                </div>
            </div>
        </div>

        <!-- longTermHours -->
        <div v-if="listNursingHours.length > 0">
            <!-- B5 -->
            <div class="card card-label">
                <div class="card-header uk-bg-accordion mt-2">
                <span>{{ "KAFS02_15" | i18n }}</span>
                </div>
            </div>

            <!-- longTermHours -->
            <div>
                <div v-for="itemLH in listNursingHours" :key="itemLH.frame">
                    <div class="row">
                        <!-- B5_1 -->
                        <div class="col-6">
                            <span>{{ itemLH.title | i18n(itemLH.frame) }}</span>
                        </div>
                    </div>

                    <div class="row">
                        <!-- B5_2 -->
                        <div class="col-6 text-left" style="font-size: 80%"><kafs00subp3 v-bind:params="itemLH.appHours" /></div>

                        <!-- B5_3 -->
                        <div class="col-6 text-right" style="font-size: 80%"><kafs00subp3 v-bind:params="itemLH.actualHours" /></div>
                    </div>

                    <div v-if="itemLH.frame < listNursingHours[listNursingHours.length - 1].frame"><hr/></div>
                </div>
            </div>
        </div>
    </div>
</template>