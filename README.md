<h1 align="center">EasyInAppBilling </h1>

<p align="center">
  <a target="_blank" href="https://www.paypal.me/RX1226" title="Donate using PayPal"><img src="https://img.shields.io/badge/paypal-donate-yellow.svg" /></a>
</p>


## How to use
1. Add the JitPack repository to your build file:
```
    allprojects {
        repositories {
            ...
            maven { url 'https://jitpack.io' }
        }
    }
```
2. Add the dependency:
```
    dependencies {
        implementation 'com.github.RX1226:EasyInAppBilling:1.0.2'
    }
```

3. Add BILLING permission in AndroidManifest.xml
```
    <uses-permission android:name="com.android.vending.BILLING" />
```
## Usage
Use RxIabHelper to process purchase
**note**
a. instance RxIabHelper object
```
public class MainActivity extends AppCompatActivity implements RxIabHelper.IabHandler{
    private String iabKey = "yourkey";
    private EasyIabHelper easyIabHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        easyIabHelper = new EasyIabHelper(this, iabKey, this);
    }

    @Override
    public void onIabSetupSuccess() {
        // Set up success
    }

    @Override
    public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
        // Handle Purchase Finished
    }
}
```
b. add easyIabHelper.unHandleActivityResult at onActivityResult
```
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (easyIabHelper.unHandleActivityResult(requestCode, resultCode, data)) {
            // IABUtil not handle, so pass it
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            // onActivityResult handled by IABUtil
        }
    }
```
c. add easyIabHelper.onDestroy() in onDestroy()
```
    @Override
    public void onDestroy() {
        easyIabHelper.onDestroy();
        super.onDestroy();
    }
```
d. use purchaseAndConsume(productId) to purchase and consume
```
    easyIabHelper.purchaseAndConsume(productId);
```
e. use purchase(productId) to purchase
```
    easyIabHelper.purchase(productId);
```

## License
	Copyright 2018 RX1226

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
