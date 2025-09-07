__Get started__

First, get your demo trial key and paste in a .env in the first layer of backend dir as APRYSE_LICENSE_KEY=xxxxxx-your-key-xxxxx

https://dev.apryse.com/

Download the appropriate directories for your OS and place them at backend/lib:
https://docs.apryse.com/core/guides/info/modules#structured-output-module


Download the appropriate server SDK here and place its jar file in backend/lib:
https://docs.apryse.com/core/guides/download


__Running project__

The easiest way to get this project started after cloning to your machine is navigating to frontend dir and running:
npm run runFrontAndBack

The idea is just to demonstrate the degree of flexibility you have with XFDF annotations, and how this could be used to overcome the limitations when converting PDF to DOCX. 

__Limitations__
-The colour is not actually limited, it works fine. So clusters of colour-organised categories can be retained in docx.

-The highlight annotations can be commented on in the right-hand panel, but when downloaded as a DOCX, this is NOT retained.

-However, I have demonstrated programmatically how you could take the content of the highlight comment from the right-hand panel and use this to build second annotation, a sticky note, on top of the highlight.

-The sticky note will persist after the conversion to DOCX.