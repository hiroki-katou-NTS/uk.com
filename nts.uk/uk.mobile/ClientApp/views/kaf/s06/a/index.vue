<template>
<div class="kafs06a">
    <div>
      <kafs00-a
        v-if="kaf000_A_Params != null"
        v-bind:params="kaf000_A_Params"
      />
    </div>

    <kafs00-b
        v-if="kaf000_B_Params != null"
        v-bind:params="kaf000_B_Params"
        v-on:kaf000BChangeDate="kaf000BChangeDate"
        v-on:kaf000BChangePrePost="kaf000BChangePrePost"
    />
    <div class="card card-label" v-if="true">
        <!-- A4_1 -->
        <div class="card-header uk-bg-accordion">
            <span>{{ "KAFS06_7" | i18n }}</span>
            <span class="badge badge-warning">必須</span>
        </div>
        <!-- A4_2 -->
        <div class="mt-1">
            <!-- A4_3 -->
            <nts-dropdown v-model="selectedValue">
                <option v-for="(item, index) in dropdownList" :key="index" :value="item.code">
                    {{item.text}}
                </option>
            </nts-dropdown>

        </div>
    </div>


    <div v-if="true" class="card card-label">
      <!-- A7_1 -->
      <div class="card-header uk-bg-accordion">
        <span>{{ "KAFS06_15" | i18n }}</span>
        <span class="badge badge-warning">任意</span>
      </div>
      <!-- A7_2 -->
      <div class="card-body">
        <nts-time-range-input v-model="workHours1" />
      </div>
    </div>
    <div v-if="true" class="card card-label">
      <!-- A8_1 -->
      <div class="card-header uk-bg-accordion">
        <span>{{ "KAFS06_16" | i18n }}</span>
        <span class="badge badge-info">任意</span>
      </div>
      <!-- A8_2 -->
      <div class="card-body">
        <nts-time-range-input v-model="workHours2" />
      </div>
    </div>

    <div v-if="true" class="card card-label">
        <!--A9_1-->
        <div class="card-header uk-bg-accordion">
            <span>{{ "KAFS06_17" | i18n }}</span>
            <span class="badge badge-warning">任意</span>
        </div>
        <div class="row px-2">
            <!--A9_2-->
            <span
                class="border-customer col p-2 m-2 text-center">
                {{"KAFS06_18" | i18n}}
            </span>

            <!--A9_3-->
            <span 
                class="border-customer col p-2 m-2 text-center">
                {{"KAFS06_19" | i18n}}
            </span>


        </div>
        <div>
            <!--A9_5-->
            <div class="row">
                <div class="float_left col text-left">{{'Com_ExsessHoliday' | i18n}}</div>
                <div class="float_right col text-right">{{'KAFS06_22' | i18n}}</div>
            </div>
            <!--A9_4-->
            <div class="card-body">
                <nts-time-editor
                    v-model="time"
                    name="'Com_ExsessHoliday'"
                    showTitle="false"
                    v-bind:enable="true"
                    time-input-type="time-duration"
                />
            </div>
        </div>
        
    </div>


    <div v-if="true" class="card card-label">
      <!-- A10_1 -->
      <div class="card-header uk-bg-accordion">
        <span>{{ "KAFS06_23" | i18n }}</span>
        <span class="badge badge-info">任意</span>
      </div>
      
    </div>
    





    <div>
      <kafs00-c
        v-if="kaf000_C_Params != null"
        v-bind:params="kaf000_C_Params"
        v-on:kaf000CChangeReasonCD="kaf000CChangeReasonCD"
        v-on:kaf000CChangeAppReason="kaf000CChangeAppReason"
      />
    </div>

</div>
</template>