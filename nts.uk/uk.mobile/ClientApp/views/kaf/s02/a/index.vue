<template>
  <div class="kafs02a">
    <div>
      <kafs00-a
        v-if="kaf000_A_Params != null"
        v-bind:params="kaf000_A_Params"
      />
    </div>
    <div class="card bg-danger top-alert uk-text-danger topError">
      <button class="btn btn-link uk-text-danger">
        <i class="fa fa-exclamation-circle" aria-hidden="true"></i>
        {{ "KAFS07_1" | i18n }}
      </button>
    </div>
    <div>
      <kafs00-b
        v-if="kaf000_B_Params != null"
        v-bind:params="kaf000_B_Params"
      />
    </div>
    <!-- A3 -->
    <div>
      <div class="card card-label">
        <div class="card-header uk-bg-accordion my-2">
          <span>{{ "KAFS02_3" | i18n }}</span>
        </div>

        <!-- workHour -->
        <div>
          <div v-for="itemWH in workHourLst" :key="itemWH.frame">
            <div class="row">
              <div class="col-6">{{ itemWH.title | i18n }}</div>
              <!-- A3_2 -->
              <div class="col-6 text-right"><kafs00subp3 v-bind:params="itemWH.actualHours" /></div>
            </div>
            <!-- A3_3 -->
            <div style="color: red">
              項目移送表参照
            </div>
            <!-- A3_1 -->
            <div class="card-body">
              <nts-time-range-input v-model="itemWH.workHours" v-bind:showTile="false" />
              <!-- A3_4 -->
              <nts-checkbox
                class="checkbox-text uk-text-dark-gray"
                v-if="itemWH.dispCheckbox"
                v-model="itemWH.workHours"
                v-bind:value="itemWH.isCheck"
                disable: itemWH.activeCheckbox
                >{{ "KAFS02_5" | i18n }}</nts-checkbox
              >
            </div>
          </div>
        </div>
        <!-- Temprory WorkHour -->
        <div>
          <div v-for="itemTH in tempWorkHourLst" :key="itemTH.frame">
            <div class="row">
              <div class="col-6">{{ itemTH.title | i18n(itemTH.frame) }}</div>
              <!-- A3_2 -->
              <div class="col-6 text-right"><kafs00subp3 v-bind:params="itemTH.actualHours" /></div>
            </div>
            <!-- A3_3 -->
            <div style="color: red">
              項目移送表参照
            </div>
            <!-- A3_1 -->
            <div class="card-body">
              <nts-time-range-input v-model="itemTH.workHours" v-bind:showTile="false" />
              <!-- A3_4 -->
              <nts-checkbox
                class="checkbox-text uk-text-dark-gray"
                v-if="itemTH.dispCheckbox"
                v-model="itemTH.workHours"
                v-bind:value="itemTH.isCheck"
                v-bind:disabled="itemTH.activeCheckbox"
                >{{ "KAFS02_5" | i18n }}</nts-checkbox
              >
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- A4 -->
    <div>
      <div class="card card-label">
        <div class="card-header uk-bg-accordion my-2">
          <span>{{ "KAFS02_8" | i18n }}</span>
        </div>

        <!-- Goout WorkHour -->
        <div v-for="itemGH in goOutLst" :key="itemGH.frame">
          <div class="row">
            <div class="col-6">{{ itemGH.title | i18n(itemGH.frame) }}</div>
            <!-- A4_2 -->
            <div class="col-6 text-right"><kafs00subp3 v-bind:params="itemGH.actualHours" /></div>
          </div>
          <!-- A4_3 -->
          <div style="color: red">
            項目移送表参照
          </div>
          <!-- A4_1 -->
          <div class="card-body">
            <nts-time-range-input v-model="itemGH.hours" v-bind:showTile="false" />
            <!-- A4_4 -->
            <div class="card-body w-100 mt-n3">
              <nts-switchbox v-for="option in dataSource" :key="option.id"
                  v-model="itemGH.swtModel" 
                  v-bind:value="option.id">
                      {{ option.name }}
              </nts-switchbox>
            </div>
            <!-- A4_5 -->
            <nts-checkbox
              class="checkbox-text uk-text-dark-gray"
              v-if="itemGH.dispCheckbox"
              v-model="itemGH.hours"
              v-bind:value="itemGH.isCheck"
              v-bind:disabled="itemGH.activeCheckbox"
              >{{ "KAFS02_5" | i18n }}</nts-checkbox
            >
          </div>
        </div>
        <!-- Add more frame zone -->
        <div class="text-center position-relative" style="height: 35px">
          <!-- A4_7 -->
          <div class="position-absolute w-100">
            <hr>
          </div>
          <!-- A4_6 -->
          <div class="position-absolute w-100 mt-1">
            <span v-on:click="addGooutHour" class="fas fa-2x fa-plus-circle" style="color: #1dade2"></span>
          </div>
        </div>
        <div class="text-center">{{ "KAFS02_10" | i18n }}</div>
      </div>
    </div>

    <!-- A5 -->
    <div>
      <div class="card card-label">
        <div class="card-header uk-bg-accordion my-2">
          <span>{{ "KAFS02_11" | i18n }}</span>
        </div>

        <!-- Break Hour -->
        <div v-for="itemBH in breakLst" :key="itemBH.frame">
          <div class="row">
            <div class="col-6">{{ itemBH.title | i18n(itemBH.frame) }}</div>
            <!-- A5_2 -->
            <div class="col-6 text-right"><kafs00subp3 v-bind:params="itemBH.actualHours" /></div>
          </div>
          <!-- A5_3 -->
          <div style="color: red">
            項目移送表参照
          </div>
          <!-- A5_1 -->
          <div class="card-body">
            <nts-time-range-input v-model="itemBH.hours" v-bind:showTile="false" />
            <!-- A5_5 -->
            <nts-checkbox
              class="checkbox-text uk-text-dark-gray"
              v-if="itemBH.dispCheckbox"
              v-model="itemBH.hours"
              v-bind:value="itemBH.isCheck"
              v-bind:disabled="itemBH.activeCheckbox"
              >{{ "KAFS02_5" | i18n }}</nts-checkbox
            >
          </div>
        </div>
        <!-- Add more frame zone -->
        <div class="text-center position-relative" style="height: 35px">
          <!-- A5_7 -->
          <div class="position-absolute w-100">
            <hr>
          </div>
          <!-- A5_6 -->
          <div class="position-absolute w-100 mt-1">
            <span v-on:click="addBreakHour" class="fas fa-2x fa-plus-circle" style="color: #1dade2"></span>
          </div>
        </div>
        <div class="text-center">{{ "KAFS02_10" | i18n }}</div>
      </div>
    </div>

    <!-- A6 -->
    <div>
      <div class="card card-label">
        <div class="card-header uk-bg-accordion my-2">
          <span>{{ "KAFS02_13" | i18n }}</span>
        </div>

        <div v-for="itemCH in childCareLst" :key="itemCH.frame">
          <div class="row">
            <div class="col-6">{{ itemCH.title | i18n(itemCH.frame) }}</div>
            <!-- A6_2 -->
            <div class="col-6 text-right"><kafs00subp3 v-bind:params="itemCH.actualHours" /></div>
          </div>
          <!-- A6_3 -->
          <div style="color: red">
            項目移送表参照
          </div>
          <!-- A6_1 -->
          <div class="card-body">
            <nts-time-range-input v-model="itemCH.hours" v-bind:showTile="false" />
            <!-- A6_5 -->
            <nts-checkbox
              class="checkbox-text uk-text-dark-gray"
              v-if="itemCH.dispCheckbox"
              v-model="itemCH.hours"
              v-bind:value="itemCH.isCheck"
              v-bind:disabled="itemCH.activeCheckbox"
              >{{ "KAFS02_5" | i18n }}</nts-checkbox
            >
          </div>
        </div>
      </div>
    </div>

    <!-- A7 -->
    <div>
      <div class="card card-label">
        <div class="card-header uk-bg-accordion my-2">
          <span>{{ "KAFS02_15" | i18n }}</span>
        </div>

        <div v-for="itemLH in longTermLst" :key="itemLH.frame">
          <div class="row">
            <div class="col-6">{{ itemLH.title | i18n(itemLH.frame) }}</div>
            <!-- A7_2 -->
            <div class="col-6 text-right"><kafs00subp3 v-bind:params="itemLH.actualHours" /></div>
          </div>
          <!-- A7_3 -->
          <div style="color: red">
            項目移送表参照
          </div>
          <!-- A7_1 -->
          <div class="card-body">
            <nts-time-range-input v-model="itemLH.hours" v-bind:showTile="false" />
            <!-- A7_5 -->
            <nts-checkbox
              class="checkbox-text uk-text-dark-gray"
              v-if="itemLH.dispCheckbox"
              v-model="itemLH.hours"
              v-bind:value="itemLH.isCheck"
              v-bind:disabled="itemLH.activeCheckbox"
              >{{ "KAFS02_5" | i18n }}</nts-checkbox
            >
          </div>
        </div>
      </div>
    </div>

    <div>
      <kafs00-c
        v-if="kaf000_C_Params != null"
        v-bind:params="kaf000_C_Params"
      />
    </div>

    <button type="button" class="btn btn-primary btn-block text-center mb-3">項目移送表参照</button>
    
    <to-top />
  </div>
</template>