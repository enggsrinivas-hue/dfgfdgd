# HR Mailer App — Build Your APK (No Coding Needed)

This folder is a complete Android app project. You don't need to write any
code — GitHub will build the .apk file for you automatically.

## What the app does
- One screen, one text box: paste the HR's email address
- One button: "Send via Gmail"
- Tapping it opens Gmail with:
  - Recipient = whatever you pasted
  - Subject = pre-filled
  - Body = pre-filled (your message)
  - Attachment = your resume (already bundled inside the app)
- You just review it in Gmail and tap Gmail's own Send button.

## Step-by-step: turn this into an APK

1. **Create a free GitHub account** (if you don't have one): go to
   https://github.com/signup and sign up with your email.

2. **Create a new repository**:
   - Click the "+" icon (top right) → "New repository"
   - Name it anything, e.g. `hr-mailer-app`
   - Keep it "Public" or "Private" (either works)
   - Click "Create repository" (don't add a README when creating it)

3. **Upload this whole folder**:
   - On the new repo's page, click "uploading an existing file"
   - Open this `HRSenderApp` folder on your computer, select ALL files and
     folders inside it, and drag them into the GitHub upload box
     (Chrome/Edge support dragging whole folders — the folder structure is
     preserved automatically)
   - Scroll down, click "Commit changes"

4. **Wait for the build**:
   - Click the "Actions" tab at the top of your repo
   - You'll see a workflow run start automatically (takes about 2–4 minutes)
   - Wait for the green checkmark ✅

5. **Download your APK**:
   - Click on the finished workflow run
   - Scroll to "Artifacts" at the bottom
   - Click "HRSenderApp-apk" to download a zip — inside it is `app-debug.apk`

6. **Install it on your Android phone**:
   - Transfer the .apk to your phone (email it to yourself, or use Google Drive)
   - Tap it to install (Android may ask you to allow "install from unknown
     sources" the first time — that's normal for apps outside the Play Store)

## If you ever want to change the template
Open `app/src/main/java/com/raja/hrsender/MainActivity.kt` in GitHub (click
the file, then the pencil icon to edit), change the `EMAIL_SUBJECT` or
`EMAIL_BODY` text, commit the change — GitHub Actions will automatically
rebuild a fresh APK for you.

To change the attached resume file: replace
`app/src/main/assets/Raja_Srinivas_Resume.docx` with your new file (keep the
exact same file name, or also update `ATTACHMENT_ASSET_NAME` in
MainActivity.kt to match the new file name).
